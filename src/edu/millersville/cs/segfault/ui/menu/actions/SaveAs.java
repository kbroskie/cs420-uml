package edu.millersville.cs.segfault.ui.menu.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.millersville.cs.segfault.ui.UMLWindow;
import edu.millersville.cs.segfault.ui.menu.MenuAction;
import edu.millersville.cs.segfault.ui.menu.ActionType;

/**************************************************************************
 * SaveAs is the class responsible for invoking the method to 
 * save the current state of the model to a user specified path.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class SaveAs extends AbstractAction  
						implements MenuAction {

	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = -4530998790701147913L;
	private static final String saveAsMenuText = "Save as...";
	
	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	private final UMLWindow window;

	/**************************************************************************
	* Constructor that builds the action.
	* @param win the frame for the interface.
	*************************************************************************/
	public SaveAs (UMLWindow win)
	{
		super(saveAsMenuText);
		window = win;
	}
	
	//*************************************************************************
	// Observers
	//*************************************************************************
	public ActionType getType() { return ActionType.SAVE_AS; }	
	
	//*************************************************************************
	// Event Listeners
	//*************************************************************************
	public void actionPerformed(ActionEvent se) {
		window.getUMLPanel().saveAs(
				window.getUMLPanel().getModel().serialize());
		window.updateTabname();
	}	
}
