package edu.millersville.cs.segfault.ui.menu.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import edu.millersville.cs.segfault.ui.UMLWindow;
import edu.millersville.cs.segfault.ui.menu.MenuAction;
import edu.millersville.cs.segfault.ui.menu.ActionType;


/**************************************************************************
 * Select is the class responsible for creating an action that will 
 * invoke the method to select or deselect all objects and relations 
 * for the current model.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class Select extends AbstractAction 
						implements MenuAction{

	//*************************************************************************
	// Static Instance Variables

	private static final long serialVersionUID = 934397787955686064L;
	
	//*************************************************************************
	// Instance Variables

	private final UMLWindow window;
	private static final String selectMenuText = "Select/Deselect All";

	//*************************************************************************
	// Constructors

	/**************************************************************************
	* Constructor that builds the action with an accelerator.
	* @param win the frame for the interface.
	*************************************************************************/
	public Select (UMLWindow win)
	{
		super(selectMenuText);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));		
		window = win;
	}
	
	//*************************************************************************
	// Observers

	@Override
	public ActionType getType() { return ActionType.SELECT; }	
	
	//*************************************************************************
	// Event Listeners

	@Override
	public void actionPerformed(ActionEvent se) {
		try {
				window.getUMLPanel().changeModel(
						window.getUMLPanel().getModel().unselectAll()); }
		catch (Exception e) {}
	}	
}
