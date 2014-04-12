package edu.millersville.cs.segfault.ui.menu.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.ui.UMLWindow;
import edu.millersville.cs.segfault.ui.menu.MenuAction;
import edu.millersville.cs.segfault.ui.menu.ActionType;

/**************************************************************************
 * New is the class responsible for invoking the method to 
 * create a new model.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class New extends AbstractAction 
					implements MenuAction {
	
	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = 447204292723445794L;
	private static final String newMenuText = "New";

	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	private final UMLWindow window;
	
	/**************************************************************************
	* Constructor that builds the action with an accelerator.
	* @param win the frame for the interface.
	*************************************************************************/
	public New (UMLWindow win) 
	{
		super(newMenuText);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));		
		window = win;
	}
	
	//*************************************************************************
	// Observers
	//*************************************************************************
	public ActionType getType() { return ActionType.NEW; }	
	
	//*************************************************************************
	// Event Listeners
	//*************************************************************************
	public void actionPerformed(ActionEvent se) {
		window.getUMLPanel().changeModel(new UMLModel());
	}	
}
