package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
//GENERAL FILE OP
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;

public class UMLPanel extends JPanel {
	
	
	private static final long serialVersionUID = 3691818181393202313L;
	UMLModel currentModel;
	LinkedList<UMLModel> undoStack;
	LinkedList<UMLModel> redoStack;
	PanelInteractionMode currentInteractionMode;
	int lastX;
	int lastY;
	static boolean hasFile;
	static File srcFile;
	boolean gridOn;
	
	enum change_types {}
	
	public UMLPanel(){
		currentModel = new UMLModel();
		undoStack = new LinkedList<UMLModel>();
		redoStack = new LinkedList<UMLModel>();
		currentInteractionMode = new DrawMode(DrawableType.OBJECT, this);
		this.addMouseListener(currentInteractionMode);
		this.addMouseMotionListener(currentInteractionMode);
		this.addKeyListener(currentInteractionMode);
		lastX = 0;
		lastY = 0;
		hasFile = false;
		srcFile = new File("");
		setFocusable(true);
		gridOn = true;
		
	}

	public UMLPanel(UMLModel new_model) {
		this();
		this.changeModel(new_model);
		repaint();
	}
	
	public void changeModel(UMLModel new_model) {
		undoStack.push(currentModel);
		this.currentModel = new_model;
		redoStack = new LinkedList<UMLModel>();
		repaint();
	}

	public void changeInteractionMode(PanelInteractionMode newMode)
	{
		currentInteractionMode.leaveMode();
		this.removeMouseListener(currentInteractionMode);
		this.removeMouseMotionListener(currentInteractionMode);
		this.removeKeyListener(currentInteractionMode);
		currentInteractionMode = newMode;
		this.addMouseListener(currentInteractionMode);
		this.addMouseMotionListener(currentInteractionMode);
		this.addKeyListener(currentInteractionMode);
		repaint();	
	}
	
	public Dimension getPreferredSize() {
		int maxX=0;
		int maxY=0;
		
		Iterator<UMLObject> oIter = currentModel.objectIterator();
		while (oIter.hasNext())
		{
			UMLObject current = oIter.next();
			int objectMaxX = current.getX() + current.getWidth();
			int objectMaxY = current.getY() + current.getHeight();
			if (maxX < objectMaxX)
			{
				maxX = objectMaxX;
			}
			if (maxY < objectMaxY)
			{
				maxY = objectMaxY;
			}
		}
		
		if (maxX < 500) { maxX = 500; }
		if (maxY < 500) { maxY = 500; }
		
		return new Dimension(maxX+10,maxY+10);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if (gridOn) {
			paintGrid(g);
		}
		
		Iterator<DrawableUML> zIter = currentModel.zIterator();
		
		while (zIter.hasNext())
		{
			zIter.next().draw(g);
		}
		
		currentInteractionMode.draw(g);
	}
	
	public void paintGrid(Graphics g) {
		int width = this.getWidth();
		int height = this.getHeight();
		
		g.setColor(Color.LIGHT_GRAY);
		
		for (int vertical = 0; vertical * DrawMode.snapDistance < width; ++vertical) {
			g.drawLine(vertical*DrawMode.snapDistance, 0, vertical*DrawMode.snapDistance, height);
		}
		
		for (int horizontal = 0; horizontal * DrawMode.snapDistance < height; ++horizontal) {
			g.drawLine(0, horizontal*DrawMode.snapDistance, width, horizontal*DrawMode.snapDistance);
		}
	}
	
	public void undo()
	{
		if (undoStack.size() > 0)
		{
			redoStack.push(currentModel);
			currentModel = undoStack.pop();
			repaint();
		}
	}
	
	public void redo()
	{
		if (redoStack.size() > 0)
		{
			undoStack.push(currentModel);
			currentModel = redoStack.pop();
			repaint();
		}
	}
	
	public UMLModel getModel()
	{
		return currentModel;
	}
	
	/******************************************************************************
	 * Save File
	 * -------------------------
	 * Takes a serialized string from Model.serialize()
	 * Writes serialized string to a file at given location
	 * 
	 * Boolean saveObject( String serialized, String path );
	 */
		private static boolean saveObject( String serialized, File file )
		{	
			if( file != null)
			{
				try{
					/*** CHECK FOR FILE, IF NOT FOUND CREATE ONE ***/
					if( !file.exists())
					{
						/***      CHECK FOR FILE EXTENSION       ***/
						/*** IF ONE DOES NOT EXIST, APPEND ".uml"***/
						if( !file.getName().contains(".") )
						{
							file = new File(file.getAbsolutePath() + ".uml");
						}
						file.createNewFile();
					}
				
					/*** SET UP FILE TO WRITE ***/
					FileWriter fw = new FileWriter(file);
					BufferedWriter bw = new BufferedWriter(fw);
				
					bw.write( serialized );		// Write the serialized string
					bw.close();					// Close the file
				
					hasFile = true;
					srcFile = file;

					return( true );
				} catch (IOException e) {
					System.err.println("ERROR: Failed to write file!");
					return( false );
				}
			} else {
				System.err.println("WARNING: JFileChooser closed unexpectedly.");
				System.err.println("Nothing saved.");
				return( false );
			}
		}
		
	/******************************************************************************
	 * Load File
	 * -------------------------
	 * Takes a path location and returns a serialized string
	 * from the contents of the file
	 * 
	 * String loadObject( String path );
	 */
		private static String loadObject( File file )
		{
			if( file != null )
			{
				try{
					String serialized = new String();
				
					/*** READ THE FILE ***/
					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
				
					while( br.ready())
					{
						serialized = serialized + br.readLine() + "\n";
					}
					br.close();						// Close the file
				
					hasFile = true;
					srcFile = file;
					return(serialized);				// Return the serialized string
				
				} catch (IOException e) {
					System.err.println("ERROR: Failed to open file!");
					return(null);
				}
			} else {
				System.err.println("ERROR: JFileChooser closed unexpectedly.");
				System.err.println("Aborting.");
				return(null);
			}

		}
/******************************************************************************
 * SaveAs
 * -------------------
 * Prompt for save information
 * 
 * Presents a graphical interface to save and passes the resulting
 * path to 'saveObject'
 */
	public boolean saveAs( String serialized )
	{
		// Create save menu through JFileChooser
	    JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter( "UML Diagram (.uml)", "uml" );
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showSaveDialog(chooser);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	// Save the file through function call to saveObject(serialized)
	    	// and return 'True' if saveObject is successful
			return(saveObject(serialized, chooser.getSelectedFile()));
	    } else {
	    	// Oh no! JFileChooser failed!!!
	    	return(false);
	    }
	}
	
/******************************************************************************
 * Save
 * -------------------
 * Save to existing file immediately
 * 
 * If file does not exist, open the saveAs interface
 */
	public boolean save( String serialized )
	{
		if(hasFile)
		{
			return( saveObject(serialized, srcFile ) );
		} else {
			return( saveAs(serialized) );
		}
	}
			
/******************************************************************************
 * Load
 * --------------------
 * Prompt for load information
 * 
 * Presents a graphical interface to load a file, passes the resulting
 * path to 'loadObject' and gives back the resultant UMLObject
 */
	public boolean load()
	{
		// Create load menu through JFileChooser
	    JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter( "UML Diagram (.uml)", "uml" );
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(chooser);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	// Load the file
	    	try{
	    		// Run the result of FileChooser through 'loadObject()'
				undoStack.push(currentModel);
				currentModel = new UMLModel( loadObject(chooser.getSelectedFile()) );;
				repaint();
				return(true);
	    	} catch(Exception e) {
	    		System.err.println("ERROR: Failed to create UML Object!");
	    		return(false);
	    	}
	    } else {
		  	//Oh no! JFileChooser failed!!!
		   	return(false);
	    }
	}

	public void getFocus() {
		requestFocusInWindow();
	}
	
	/******************************************************************************
	 * Select Controller
	 * --------------------
	 * Handles Alternation between selectAll and deselectAll
	 */
	public void select()
	{
		if(fullSelection())
		{
			deselectAll();
		} else {
			selectAll();
		}
		this.repaint();
	}
	
	/******************************************************************************
	 * All Selected?
	 * --------------------
	 * Checks whether the entire model is selected
	 */
	public boolean fullSelection()
	{
		// Iterate through the set of models
		Iterator<DrawableUML> zIter = this.getModel().zIterator();
		while( zIter.hasNext())
		{
			// Check model's selection state
			if(!( zIter.next().isSelected()))
			{
				return( false );
			}
		}
		return( true );
	}
	
	/******************************************************************************
	 * Select All
	 * --------------------
	 * Sets every element in the panel to a selected state
	 */
	private void selectAll()
	{
		// Iterate through the set of models
		Iterator<DrawableUML> zIter = this.getModel().zIterator();
		while( zIter.hasNext())
		{
			// Set each model to a selected state
			this.changeModel(this.getModel().select(zIter.next()));
		}
		this.repaint();
	}
	
	/******************************************************************************
	 * Select Deselect All
	 * --------------------
	 * Sets every element in the panel to an un-selected state
	 */
	private void deselectAll()
	{
		// Iterate through the set of models
		Iterator<DrawableUML> zIter = this.getModel().zIterator();
		while( zIter.hasNext())
		{
			// Set each model to a selected state
			this.changeModel(this.getModel().unselect(zIter.next()));
		}
		this.repaint();
	}

	public void showGrid() {
		this.gridOn = true;
	}
	
	public void hideGrid() {
		this.gridOn = false;
	}
}
