package edu.millersville.cs.segfault.ui.menu;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**************************************************************************
 * UMLMenuBar is the class responsible for instantiating 
 * a menubar.
 * @author Kimberlyn Broskie
 *************************************************************************/

public class UMLMenuBar extends JMenuBar {
	 
	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = 8104072242520475736L;
		
	//*************************************************************************
	// Constructors	
	//*************************************************************************
	
	/**************************************************************************
	 * Constructor to build the menu bar.
	 * @param wFrame the frame for the interface
	 * @param umlPanel the panel for the current UML model
	 *************************************************************************/
	public UMLMenuBar (JFrame wFrame) {
		super();
				   
		// Create the sub-menus.
		JMenu fileSubmenu = new UMLFileMenu(wFrame);
		JMenu editSubmenu = new UMLEditMenu(wFrame);
		   
		// Add the submenus to the menu bar.
		this.add(fileSubmenu);
		this.add(editSubmenu);
		   
		// Set the window's menu bar.
		wFrame.setJMenuBar(this);
	}
}
