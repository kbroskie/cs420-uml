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
 * Cut is the class responsible for deleting a selection while adding
 * the selection to a paste buffer
 * @author Wesley DeMarco, Kimberlyn Broskie
 *************************************************************************/
public class Cut extends AbstractAction 
						implements MenuAction{
	
	//*************************************************************************
	// Static Instance Variables

	private static final long serialVersionUID = 1596916755400971266L;
	private static final String cutMenuText = "Cut";

	//*************************************************************************
	// Instance Variables

	private final UMLWindow window;

	//*************************************************************************
	// Constructors

	/**************************************************************************
	* Constructor that builds the action with an accelerator.
	* @param win the frame for the interface.
	*************************************************************************/
	public Cut (UMLWindow win)
	{
		super(cutMenuText);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));		
		window = win;
	}
	
	//*************************************************************************
	// Observers

	@Override
	public ActionType getType() { return ActionType.CUT; }	
	
	//*************************************************************************
	// Event Listeners

	@Override
	public void actionPerformed(ActionEvent se) {
		try {
			// Add current selection to the pastebuffer
			window.setPasteBuffer(window.getUMLPanel().getModel().selectedSet());
				window.getUMLPanel().changeModel(
						// Delete selected
						window.getUMLPanel().getModel().deleteSelected()); }
		catch (Exception e) {}
	}	
}
