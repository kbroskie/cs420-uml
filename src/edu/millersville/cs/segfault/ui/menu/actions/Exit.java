package edu.millersville.cs.segfault.ui.menu.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.millersville.cs.segfault.ui.menu.ActionType;
import edu.millersville.cs.segfault.ui.menu.MenuAction;

/**************************************************************************
 * Exit is the class responsible to invoke the action to 
 * exit the program. 
 * @author Kimberlyn Broskie
 *************************************************************************/
public class Exit extends AbstractAction  
						implements MenuAction {
	
	//*************************************************************************
	// Static Instance Variables

	private static final long serialVersionUID = 6496835257740499669L;
	private static final String exitMenuText = "Exit";
	
	//*************************************************************************
	// Constructors

	/**************************************************************************
	 * Constructor that builds the exit action.
	 *************************************************************************/
	public Exit ()
	{
		super(exitMenuText);
	}
	
	//*************************************************************************
	// Observers

	@Override
	public ActionType getType() { return ActionType.EXIT; }	
	
	//*************************************************************************
	// Event Listeners

	@Override
	public void actionPerformed(ActionEvent se) {
			System.exit(0);
	}	
}
