package edu.millersville.cs.segfault.ui.menu.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.ui.UMLWindow;
import edu.millersville.cs.segfault.ui.menu.ActionType;
import edu.millersville.cs.segfault.ui.menu.MenuAction;

/**************************************************************************
 * Paste is a class to support paste buffer actions
 * @author Wesley DeMarco, Kimberlyn Broskie
 *************************************************************************/
public class Paste extends AbstractAction
						implements MenuAction {

	//*************************************************************************
	// Static Instance Variables

	private static final long serialVersionUID = -401022597982490050L;
	private static final String pasteMenuText = "Paste";

	//*************************************************************************
	// Instance Variables

	private final UMLWindow window;

	//*************************************************************************
	// Constructors

	/**************************************************************************
	* Constructor that builds the action with an accelerator.
	* @param win the frame for the interface.
	*************************************************************************/
	public Paste (UMLWindow win)
	{
		super(pasteMenuText);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK));		
		window = win;
	}
	
	//*************************************************************************
	// Observers

	@Override
	public ActionType getType() { return ActionType.PASTE; }	

	//*************************************************************************
	// Event Listeners

	@Override
	public void actionPerformed(ActionEvent se) {
		try{
			UMLModel model = new UMLModel(window.getUMLPanel().getModel());
			Iterator<DrawableUML> iter = window.getPasteBuffer().iterator();
			model = model.deleteSelected();
			while( iter.hasNext()) {
				model = model.add(iter.next());
			}
			
			window.getUMLPanel().changeModel( model );		
		} catch(Exception e) {}
	}		
}
