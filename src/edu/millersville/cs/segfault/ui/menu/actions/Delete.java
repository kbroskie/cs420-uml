package edu.millersville.cs.segfault.ui.menu.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import edu.millersville.cs.segfault.ui.UMLWindow;
import edu.millersville.cs.segfault.ui.menu.ActionType;
import edu.millersville.cs.segfault.ui.menu.MenuAction;


/**************************************************************************
 * Delete is the class responsible for invoking the method to delete 
 * all objects and relations for the current model.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class Delete extends AbstractAction 
						implements MenuAction{
	
	//*************************************************************************
	// Static Instance Variables

	private static final long serialVersionUID = 1596916755400971266L;
	private static final String deleteMenuText = "Delete";

	//*************************************************************************
	// Instance Variables
	
	private final UMLWindow window;

	//*************************************************************************
	// Constructors

	/**************************************************************************
	* Constructor that builds the delete action with an accelerator.
	* @param win the frame for the interface.
	*************************************************************************/
	public Delete (UMLWindow win)
	{
		super(deleteMenuText);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE , 0));		
		window = win;
	}
	
	//*************************************************************************
	// Observers

	@Override
	public ActionType getType() { return ActionType.DELETE; }	
	
	//*************************************************************************
	// Event Listeners

	@Override
	public void actionPerformed(ActionEvent se) {
		try {
				window.getUMLPanel().changeModel(
						window.getUMLPanel().getModel().deleteSelected()); }
		catch (Exception e) {}
	}	
}
