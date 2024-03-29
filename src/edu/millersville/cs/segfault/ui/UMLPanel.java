package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;

/**************************************************************************
 * A custom subclass of JPanel which contains a UMLModel and displays it
 * as a panel in a swing GUI.
 * 
 * @author Daniel Rabiega
 *************************************************************************/

public class UMLPanel extends JPanel {
	
	//************************************************************************
	// Static Members
	
	private static final long serialVersionUID = 3691818181393202313L;

	
	//************************************************************************
	// Instance Variables
	
	private UMLModel currentModel;
	private LinkedList<UMLModel> undoStack;
	private LinkedList<UMLModel> redoStack;
	private PanelInteractionMode currentInteractionMode;
	boolean gridOn;
	UMLWindow parent;
	boolean hasFile;
	File srcFile;
	
	
	//************************************************************************
	// Constructors
	
	/*************************************************************************
	 * Creates a new UMLPanel with default values.
	 *************************************************************************/
	public UMLPanel(UMLWindow parent){
		currentModel = new UMLModel();
		this.parent = parent;
		undoStack = new LinkedList<UMLModel>();
		redoStack = new LinkedList<UMLModel>();
		currentInteractionMode = new SelectionMode(parent);
		this.addMouseListener(currentInteractionMode);
		this.addMouseMotionListener(currentInteractionMode);
		this.addKeyListener(currentInteractionMode);
		hasFile = false;
		srcFile = new File("");
		setFocusable(true);
		gridOn = false;
	}

	/*************************************************************************
	 * Creates a new UMLPanel displaying a given model.
	 * @param new_model The model to display.
	 *************************************************************************/
	public UMLPanel(UMLWindow parent, UMLModel new_model) {
		this(parent);
		this.changeModel(new_model);
		repaint();
	}
	
	//************************************************************************
	// Observers
	
	public Dimension getPreferredSize() {
		int maxX=0;
		int maxY=0;
		
		Iterator<UMLObject> oIter = currentModel.objectIterator();
		while (oIter.hasNext())
		{
			UMLObject current = oIter.next();
			int objectMaxX = current.origin.x + current.size.width;
			int objectMaxY = current.origin.y + current.size.height;
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
	
	/*************************************************************************
	 * Returns the current state of the model.
	 *************************************************************************/

	public UMLModel getModel()
	{
		return currentModel;
	}
	
	/******************************************************************************
	 * All Selected?
	 * Checks whether the entire model is selected
	 * @return boolean
	 ******************************************************************************/
	public boolean fullSelection()
	{
		// Iterate through the set of models
		Iterator<DrawableUML> zIter = this.getModel().iterator();
		
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
	
	//************************************************************************
	// Mutators
	
	/*************************************************************************
	 * Replaces the current state of the model with a new version and places
	 * the previous version on the undo stack.
	 ************************************************************************/
	public void changeModel(UMLModel new_model) {
		undoStack.push(currentModel);
		this.currentModel = new_model;
		redoStack = new LinkedList<UMLModel>();
		repaint();
	}
	
	public void minorChange(UMLModel newModel) {
		this.currentModel = newModel;
	}

	/*************************************************************************
	 * Reroutes user interaction with the UMLPanel to the given mode.
	 *************************************************************************/
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
	
	/*************************************************************************
	 * Undoes the last change to the model which used changeModel	
	 *************************************************************************/
	public void undo()
	{
		if (undoStack.size() > 0)
		{
			redoStack.push(currentModel);
			currentModel = undoStack.pop();
			repaint();
		}
	}
	
	/*************************************************************************
	 * Redoes an undone change to the model.
	 *************************************************************************/
	public void redo()
	{
		if (redoStack.size() > 0)
		{
			undoStack.push(currentModel);
			currentModel = redoStack.pop();
			repaint();
		}
	}
	
	/*************************************************************************
	 * Public Save
	 * Prompts the user with the JFileChooser to obtain information about
	 * the file's parameters to be saved. This will present a graphical
	 * interface and then pass the resulting path to 'saveObject()'
	 * @param serialized
	 * @return boolean
	 *************************************************************************/
	public boolean saveAs( String serialized )
	{
		// Create save menu through JFileChooser
	    JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter( "UML Diagram (.uml)", "uml" );
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showSaveDialog(chooser);
	    
	    // Save the file through a call to 'saveObject()'
	    // returning saveObject()'s success status
	    if(returnVal == JFileChooser.APPROVE_OPTION)
	    {
	    	return(saveObject(serialized, chooser.getSelectedFile()));
	    } else {
	    	return(false);
	    }
	}
	
	/*************************************************************************
	 * Save Controller
	 * Determine whether a given project has a file associated with it already
	 * If so, simply overwrite that file, otherwise, prompt the user to create
	 * one through the saveAs() function.
	 * @param serialized
	 * @return boolean
	 *************************************************************************/
	public boolean save( String serialized )
	{
		// CHECK FOR THE FILE
		if(hasFile)
		{
			return( saveObject(serialized, srcFile ) );
		} else {
			return( saveAs(serialized) );
		}
	}
			
	/*************************************************************************
	 * Public Load
	 * Prompts the user with the JFileChooser to obtain information about
	 * the file to be loaded. This will prese																																																																																																													nt a graphical interface and
	 * then pass the resulting path to 'LoadObject()' which will then give
	 * back the resultant 'UMLObject'.
	 * @return boolean
	 *************************************************************************/
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
	    		System.err.println("Load failed: " + e.getMessage());
	    		e.printStackTrace();
	    		return(false);
	    	}
	    } else {
		   	return(false);
	    }
	}

	/**************************************************************************
	 * Returns the current filename.
	 * @return the current saved filename.
	 *************************************************************************/
	public String getFilename() {
		return srcFile.toString();
	}
	
	/******************************************************************************
	 * Select Controller
	 * Handles Alternation between selectAll and deselectAll
	 ******************************************************************************/
	public void select()
	{
		// CHECK IF ALL ELEMENTS ARE SELECTED	
		// If so, deselect everything. Otherwise, ensure
		// that the application is in selection mode and set
		// all objects to the 'selected' state
		if(fullSelection())
		{
			// De-select everything
			try {
				this.changeModel(this.getModel().unselectAll());
			} catch (Exception e) {
				System.out.println("Could not unselect all:" + e.getMessage());
			}
		} else {
			// Enter selection mode
			this.changeInteractionMode(new SelectionMode(parent));
			selectAll();
		}
		
		// Update the screen to reflect changes
		this.repaint();
	}
	
	/******************************************************************************
	 * Select All
	 * Sets every element in the panel to a selected state
	 ******************************************************************************/
	public void selectAll()
	{
		// Iterate through the set of models
		Iterator<DrawableUML> zIter = this.getModel().iterator();
		
		while( zIter.hasNext())
		{
			// Set each model to a selected state
			try {
				this.changeModel(this.getModel().select(zIter.next()));
			} catch (Exception e) {
				System.out.println("Could not select:" + e.getMessage());
			}
		}
	
		// Update the screen to reflect changes
		this.repaint();
	}

	public void showGrid() {
		this.gridOn = true;
	}
	
	public void hideGrid() {
		this.gridOn = false;
	}

	/**************************************************************************
	 * Save File
	 * Takes a serialized String from Model.serialize() and writes
	 * that string to a file at a given location
	 * @param serialized file
	 * @return boolean
	 *************************************************************************/
	private boolean saveObject( String serialized, File file)
	{
		if( file != null)
		{
			try{
				// CHECK FOR FILE, IF NOT FOUND CREATE ONE
				if( !file.exists())
				{
					// CHECK FOR FILE EXTENSION         
					// IF ONE DOES NOT EXIST, APPEND ".uml" 
					if( !file.getName().contains(".") )
					{
						file = new File(file.getAbsolutePath() + ".uml");
					}
					file.createNewFile();
				}
			
				// WRITE TO THE FILE		 	  
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
	
	/*************************************************************************
	 * Load File
	 * Takes a path location and returns a serialized string
	 * corresponding to the contents found in the file at the
	 * given location.
	 * @param file
	 * @return String
	 *************************************************************************/
	private String loadObject( File file )
	{
		if( file != null )
		{
			try{
				String serialized = new String();
			
				// READ THE FILE			
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
			
				// Anything left?
				while( br.ready())
				{
					// Append the next line to the serialized string
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
	
	//************************************************************************
	// Drawing Methods
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if (gridOn) {
			paintGrid(g);
		}
		
		Iterator<DrawableUML> zIter = currentModel.iterator();
		
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
	
	public void getFocus() {
		requestFocusInWindow();
	}
}
