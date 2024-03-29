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
 * Undo is the class responsible for invoking the method to undo the 
 * the last action performed for a given model.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class Undo extends AbstractAction 
						implements MenuAction{

	//*************************************************************************
	// Static Instance Variables

	private static final long serialVersionUID = -7350470434363782769L;
	private static final String undoMenuText = "Undo";

	//*************************************************************************
	// Instance Variables

	private final UMLWindow window;

	//*************************************************************************
	// Constructors

	/**************************************************************************
	* Constructor that builds the undo action with an accelerator.
	* @param win the frame for the interface.
	*************************************************************************/
	public Undo (UMLWindow win)
	{
		super(undoMenuText);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));		
		window = win;
	}

	//*************************************************************************
	// Observers

	@Override
	public ActionType getType() { return ActionType.UNDO; }	

	//*************************************************************************
	// Event Listeners

	@Override
	public void actionPerformed(ActionEvent se) {
		window.getUMLPanel().undo();
	}	
}