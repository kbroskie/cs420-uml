package edu.millersville.cs.segfault.ui.menu;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import edu.millersville.cs.segfault.ui.UMLWindow;

/**************************************************************************
 * FileMenu is the class responsible for instantiating 
 * the File submenu and handling the user selections for the submenu.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class FileMenu extends JMenu {
	
	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = 2052974378829469666L;

	private static final String fileMenuText = "File";
	
	
	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	private final UMLWindow parentWindow;
	
	//*************************************************************************
	// Constructors	
	//*************************************************************************
	
	/**************************************************************************
	 * Constructor to build the File submenu, with each submenu option
	 * having a key accelerator.
	 * @param parent the frame for the interface
	 *************************************************************************/
	public FileMenu (UMLWindow parent) {
		super(fileMenuText);
		parentWindow = parent;
		
		// Generate all the edit action types and add them to the submenu.
		JMenuItem newItem;
		Action newAction;
		   
		for (ActionType type : ActionType.fileMenuTypeList()) {
			// Add a menu separator to group similar actions.
			if (type.isDifferentActionGroup) {
				addSeparator();
			 }
			try {
				   newAction = (Action)ActionType.makeFileMenuAction(type, parentWindow);
				   newItem = new JMenuItem(newAction);
				   add (newItem); 
			} catch (Exception e) {}
		}
	}
}
