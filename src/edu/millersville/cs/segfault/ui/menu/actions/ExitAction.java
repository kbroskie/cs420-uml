package edu.millersville.cs.segfault.ui.menu.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.millersville.cs.segfault.ui.menu.MenuAction;
import edu.millersville.cs.segfault.ui.menu.ActionType;

/**************************************************************************
 * ExitAction is the class responsible to invoke the action to 
 * exit the program. 
 * @author Kimberlyn Broskie
 *************************************************************************/
public class ExitAction extends AbstractAction  
						implements MenuAction {

	private static final long serialVersionUID = 6496835257740499669L;
	private static final String exitMenuText = "Exit";
	
	/**************************************************************************
	* Constructor that builds the action.
	*************************************************************************/
	public ExitAction ()
	{
		super(exitMenuText);
	}
	
	//*************************************************************************
	// Observers
	//*************************************************************************
	public ActionType getType() { return ActionType.EXIT; }	
	
	//*************************************************************************
	// Event Listeners
	//*************************************************************************
	// Handles the event generated when the user selects 
	// the exit option from the File menu.
	public void actionPerformed(ActionEvent se) {
			System.exit(0);
	}	
}
