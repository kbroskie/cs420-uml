package edu.millersville.cs.segfault.model;

// GENERAL FILE OP
import java.io.File;
import java.io.IOException;

// JFILECHOOSER
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;


// READ
import java.io.FileReader;
import java.io.BufferedReader;


// WRITE
import java.io.FileWriter;
import java.io.BufferedWriter;

public class UMLFileOp {
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
 * Save
 * -------------------
 * Prompt for save information
 * 
 * Presents a graphical interface to save and passes the resulting
 * path to 'saveObject'
 */
	public static boolean save( String serialized )
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
