package edu.millersville.cs.segfault.ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import edu.millersville.cs.segfault.ui.UMLWindow;

/**************************************************************************
 * MenuBar is the class responsible for instantiating 
 * a menubar.
 * @author Kimberlyn Broskie
 *************************************************************************/

public class MenuBar extends JMenuBar {
	 
	//*************************************************************************
	// Static Instance Variables

	private static final long serialVersionUID = 8104072242520475736L;
		
	//*************************************************************************
	// Constructors	
	
	/**************************************************************************
	 * Constructor to build the menu bar.
	 * @param parent the frame for the interface
	 *************************************************************************/
	public MenuBar (UMLWindow parent) {
		super();
				   
		// Create the sub-menus.
		JMenu fileSubmenu = new FileMenu(parent);
		JMenu editSubmenu = new EditMenu(parent);
		OptionsMenu optionsMenu = new OptionsMenu(parent);
		   
		// Add the submenus to the menu bar.
		add(fileSubmenu);
		add(editSubmenu);
		add(optionsMenu);
		   
		// Set the window's menu bar.
		parent.setJMenuBar(this);
	}
}
