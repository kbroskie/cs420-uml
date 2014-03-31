package edu.millersville.cs.segfault.ui;

import javax.swing.JFrame;

/**************************************************************************
 * WindowController is the class responsible for
 * instantiating an instance of a UMLWindow.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class WindowController {

	//*************************************************************************
	// Constructors	
	//*************************************************************************
	
	// Constructs the UML window.
	private WindowController() {
	    
		// Set the look and feel.
	    if (System.getProperty("mrj.version") != null) {
	    	System.setProperty("apple.laf.useScreenMenuBar", "true");
	    }
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    
	    new UMLWindow();
	}
	
	
	//********************************************************************
	// Main method
	//********************************************************************

	/**************************************************************************
	 * Creates and shows the UML editor GUI.
	 * @param args are not used
	 *************************************************************************/
	public static void main(String[] args) {
	   javax.swing.SwingUtilities.invokeLater(new Runnable() {
		   public void run() {
			   new WindowController();
		   }
	   });
	}
}
