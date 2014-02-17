package edu.millersville.cs.segfault.model;

import java.io.File;
import java.io.IOException;

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
 * Bool saveObject( String serialized, String path );
 */
	private static boolean saveObject( String serialized, String path )
	{
		try{
			File file = new File(path);
		
			/*** CHECK FOR FILE, IF NOT FOUND CREATE ONE ***/
			if( !file.exists())
			{
					file.createNewFile();
			}
			
			/*** SET UP FILE TO WRITE ***/
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write( serialized );						// Write the serialized string
			bw.close();									// Close the file
			
			return( true );
		} catch (IOException e) {
			System.err.println("ERROR: Failed to write file!");
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
	private static String loadObject( String path )
	{
		try{
			File file = new File(path);
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
		String path = new String();
		
		// Create save menu and get string
		path = ""; // Output of save menu
		
		// Save the file
		return(saveObject(serialized, path));
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
		String path = new String();
		
		// Create load menu and get string
		path = ""; // Output of load menu
		
		// Load the file
		try{
			return( new UMLObject( loadObject(path) ) );
		} catch(Exception e) {
			System.err.println("ERROR: Failed to create UML Object!");
			return(null);
		}
	}
}
