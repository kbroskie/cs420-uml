package edu.millersville.cs.segfault.ui.menu.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import edu.millersville.cs.segfault.ui.UMLWindow;
import edu.millersville.cs.segfault.ui.menu.ActionType;
import edu.millersville.cs.segfault.ui.menu.MenuAction;


/**************************************************************************
 * Copy is the class responsible for adding a selection to a paste buffer
 * without making any mutations to the model.
 * @author Wesley DeMarco, Kimberlyn Broskie
 *************************************************************************/
public class Copy extends AbstractAction 
						implements MenuAction{
	
	//*************************************************************************
	// Static Instance Variables

	private static final long serialVersionUID = 1596916755400971266L;
	private static final String cutMenuText = "Copy";

	//*************************************************************************
	// Instance Variables
	private final UMLWindow window;

	//*************************************************************************
	// Constructors
	
	/**************************************************************************
	* Constructor that builds the action with an accelerator.
	* @param win the frame for the interface.
	*************************************************************************/
	public Copy (UMLWindow win)
	{
		super(cutMenuText);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));		
		window = win;
	}
	
	//*************************************************************************
	// Observers
	
	@Override
	public ActionType getType() { return ActionType.COPY; }	
	
	//*************************************************************************
	// Event Listeners

	@Override
	public void actionPerformed(ActionEvent se) {
		try {
			// Add current selection to the pastebuffer
			window.setPasteBuffer(window.getUMLPanel().getModel().selectedSet());
		}
		catch (Exception e) {}
	}	
}
