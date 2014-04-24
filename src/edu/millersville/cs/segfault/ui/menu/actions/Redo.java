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
 * Redo is the class responsible for invoking the method to redo the 
 * the last action performed for a given model.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class Redo extends AbstractAction 
						implements MenuAction{

	//*************************************************************************
	// Static Instance Variables

	private static final long serialVersionUID = -2648065284343268597L;
	private static final String redoMenuText = "Redo";

	//*************************************************************************
	// Instance Variables

	private final UMLWindow window;

	//*************************************************************************
	// Constructors

	/**************************************************************************
	* Constructor that builds the redo action with an accelerator.
	* @param win the frame for the interface.
	*************************************************************************/
	public Redo (UMLWindow win)
	{
		super(redoMenuText);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK));		
		window = win;
	}
	
	//*************************************************************************
	// Observers
	
	@Override
	public ActionType getType() { return ActionType.REDO; }	
	
	//*************************************************************************
	// Event Listeners

	@Override
	public void actionPerformed(ActionEvent se) {
		window.getUMLPanel().redo();
	}	
}
