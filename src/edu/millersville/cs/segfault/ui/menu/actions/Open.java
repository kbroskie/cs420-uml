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
 * Save is the class responsible for invoking the method to 
 * load a model.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class Open extends AbstractAction
						implements MenuAction {

	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = -401022597982490050L;
	private static final String openMenuText = "Open";

	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	private final UMLWindow window;

	/**************************************************************************
	* Constructor that builds the action with an accelerator.
	* @param win the frame for the interface.
	*************************************************************************/
	public Open (UMLWindow win)
	{
		super(openMenuText);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));		
		window = win;
	}
	
	//*************************************************************************
	// Observers
	//*************************************************************************
	public ActionType getType() { return ActionType.OPEN; }	

	//*************************************************************************
	// Event Listeners
	//*************************************************************************
	public void actionPerformed(ActionEvent se) {
		window.getUMLPanel().load();
	}		
}
