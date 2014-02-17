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
 * Bool UMLFileOP.save( String serialized, String path );
 */
	public static boolean save( String serialized, String path )
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
 * String UMLFileOP.load( String path );
 */
	public String load( String path )
	{
		try{
			File file = new File(path);
			String serialized = new String();
			
			/*** READ THE FILE ***/
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			serialized = br.readLine();						// Store the data
			br.close();										// Close the file
			
			return(serialized);								// Return the serialized string
			
		} catch (IOException e) {
			System.err.println("ERROR: Failed to open file!");
			return(null);
		}

	}
}
