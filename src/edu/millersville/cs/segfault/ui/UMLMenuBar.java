package edu.millersville.cs.segfault.ui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * UMLMenuBar is the class responsible for instantiating 
 * a menubar composed of submenus.
 * @author Kimberlyn Broskie
 *
 */ 
class UMLMenuBar extends JMenuBar {
	 
	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	
	private static final long serialVersionUID = 8104072242520475736L;
	
	
	//*************************************************************************
	// Constructors	
	//*************************************************************************
	
	/*
	 * Constructor to build the menu bar.
	 * @param wFrame the frame for the interface
	 * @param umlPanel the panel for the current UML model
	 */
	public UMLMenuBar (JFrame wFrame, UMLPanel umlPanel) {
		super();
				   
		// Create the sub-menus.
		JMenu fileSubmenu = new UMLFileMenu(wFrame, umlPanel);
		JMenu editSubmenu = new UMLEditMenu(wFrame, umlPanel);
		   
		// Add the submenus to the menu bar.
		this.add(fileSubmenu);
		this.add(editSubmenu);
		   
		// Set the window's menu bar.
		wFrame.setJMenuBar(this);
	}
}
