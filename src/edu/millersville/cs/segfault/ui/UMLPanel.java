package edu.millersville.cs.segfault.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

//GENERAL FILE OP
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

import javax.swing.JPanel;

import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.UMLObject;

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
	
	enum change_types {}
	
	public UMLPanel(){
		currentModel = new UMLModel();
		undoStack = new LinkedList<UMLModel>();
		redoStack = new LinkedList<UMLModel>();
		currentInteractionMode = new SelectionMode(this);
		this.addMouseListener(currentInteractionMode);
		this.addMouseMotionListener(currentInteractionMode);
		this.addKeyListener(currentInteractionMode);
		lastX = 0;
		lastY = 0;
		hasFile = false;
		srcFile = new File("");
		setFocusable(true);
		
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
		currentInteractionMode = newMode;
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
		
		Iterator<DrawableUML> zIter = currentModel.zIterator();
		
		while (zIter.hasNext())
		{
			zIter.next().draw(g);
		}
		
		currentInteractionMode.draw(g);
	}
	
	public void undo()
	{
		if (undoStack.size() > 0)
		{
			redoStack.push(currentModel);
			currentModel = undoStack.pop();
		}
	}
	
	public void redo()
	{
		if (redoStack.size() > 0)
		{
			undoStack.push(currentModel);
			currentModel = redoStack.pop();
		}
	}
	
	public UMLModel model()
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
							file.renameTo(new File(file.getName() + ".uml"));
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
	public static boolean saveAs( String serialized )
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
	public static boolean save( String serialized )
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
	public static UMLObject load()
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
	    		return( new UMLObject( loadObject(chooser.getSelectedFile()) ) );
	    	} catch(Exception e) {
	    		System.err.println("ERROR: Failed to create UML Object!");
	    		return(null);
	    	}
	    } else {
		  	//Oh no! JFileChooser failed!!!
		   	return(null);
	    }
	}
}
