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
 * Save is the class responsible for invoking the method to save the 
 * current state of the model.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class Save extends AbstractAction 
						implements MenuAction{

	//*************************************************************************
	// Static Instance Variables

	private static final long serialVersionUID = -7422025816728887633L;
	private static final String saveMenuText = "Save";
	
	//*************************************************************************
	// Instance Variables

	private final UMLWindow window;

	//*************************************************************************
	// Constructors

	/**************************************************************************
	* Constructor that builds the save action with an accelerator.
	* @param win the frame for the interface.
	*************************************************************************/
	public Save (UMLWindow win)
	{
		super(saveMenuText);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));		
		window = win;
	}
	
	//*************************************************************************
	// Observers

	@Override
	public ActionType getType() { return ActionType.SAVE; }	
	
	//*************************************************************************
	// Event Listeners

	@Override
	public void actionPerformed(ActionEvent se) {
		window.getUMLPanel().save(
				window.getUMLPanel().getModel().serialize());
		window.updateTabname();
	}	
}
