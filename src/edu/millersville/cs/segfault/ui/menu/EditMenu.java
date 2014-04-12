package edu.millersville.cs.segfault.ui.menu;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import edu.millersville.cs.segfault.ui.UMLWindow;

/**************************************************************************
 * EditMenu is the class responsible for instantiating
 * the Edit submenu and handling the user selections for the submenu.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class EditMenu extends JMenu {
	
	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = -3884476289367851522L;
	
	private static final String editMenuText = "Edit";
	
	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	private final UMLWindow parentWindow;
	
	//*************************************************************************
	// Constructors	
	//*************************************************************************
		
	/**************************************************************************
	* Constructor that builds the Edit submenu, with each submenu option
	* having a key accelerator.
	* @param parent the frame for the interface.
	*************************************************************************/
	public EditMenu(UMLWindow parent) {
	   super(editMenuText);
	   parentWindow = parent;
	   
	   // Generate all the edit action types and add them to the submenu.
	   JMenuItem newItem;
	   Action newAction;
	   
	   for (ActionType type : ActionType.editMenuTypeList()) {
		   // Add a menu separator to group similar actions.
		   if (type.isDifferentActionGroup) {
			   addSeparator();
		   }
		   try {
			   newAction = (Action)ActionType.makeEditMenuAction(type, parentWindow);
			   newItem = new JMenuItem(newAction);
			   add (newItem); 
		   } catch (Exception e) {}
		}
	}
}
