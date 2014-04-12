package edu.millersville.cs.segfault.ui.menu;

import java.util.LinkedList;

import javax.swing.ImageIcon;

import edu.millersville.cs.segfault.ui.UMLWindow;
import edu.millersville.cs.segfault.ui.menu.actions.Delete;
import edu.millersville.cs.segfault.ui.menu.actions.Exit;
import edu.millersville.cs.segfault.ui.menu.actions.New;
import edu.millersville.cs.segfault.ui.menu.actions.Open;
import edu.millersville.cs.segfault.ui.menu.actions.Redo;
import edu.millersville.cs.segfault.ui.menu.actions.Save;
import edu.millersville.cs.segfault.ui.menu.actions.SaveAs;
import edu.millersville.cs.segfault.ui.menu.actions.Select;
import edu.millersville.cs.segfault.ui.menu.actions.Undo;


/*****************************************************************************
 * An enum whose types signify the different menu actions.        			 *
 * @author Kimberlyn Broskie                                                 *
 *****************************************************************************/
public enum ActionType {
	//Name      ActionType? Different Action Group?
	NEW      	(true, false),
	OPEN        (true, false),
	SAVE 		(true, true),
	SAVE_AS	    (true, false),
	EXIT  		(true, true),
	UNDO		(false, false),
	REDO        (false, false),
	SELECT	 	(false, true),
	DELETE 		(false, false);
	
	// Determine whether the variable is a file or edit action type.
	public final boolean      isFileAction;
	
	// Determine whether the variable is the start of a similar group.
	public final boolean 	  isDifferentActionGroup;
	
	public final ImageIcon    icon; 
	
	ActionType(boolean isFileAction, boolean isDifferentActionGroup) {
		this.isFileAction = isFileAction;
		this.isDifferentActionGroup = isDifferentActionGroup;
		this.icon     = new ImageIcon("img/32/" + this.name() + ".png");
	}
	

	/*****************************************************************************
	 * Returns all of the types in this enum.        			 				 *
	 *****************************************************************************/
	public static LinkedList<ActionType> typeList() {
		LinkedList<ActionType> types = new LinkedList<ActionType>();
		for (ActionType type: NEW.getDeclaringClass().getEnumConstants()) {
			types.add(type);
		}
		return types;
	}

	/*****************************************************************************
	 * Returns all of the types in this enum that correspond to file actions     *
	 *****************************************************************************/
	public static LinkedList<ActionType> fileMenuTypeList() {
		LinkedList<ActionType> types = new LinkedList<ActionType>();
		for (ActionType type: NEW.getDeclaringClass().getEnumConstants()) {
			if (type.isFileAction) {
				types.add(type);
			}
		}
		return types;
	}
	
	/*****************************************************************************
	 * Returns all of the types in this enum that correspond to edit actions     *
	 *****************************************************************************/
	public static LinkedList<ActionType> editMenuTypeList() {
		LinkedList<ActionType> types = new LinkedList<ActionType>();
		for (ActionType type: NEW.getDeclaringClass().getEnumConstants()) {
			if (!type.isFileAction) types.add(type);
		}
		return types;
	}
	
	//************************************************************************
	// Factories

	// Action Factories - Call a specialized factory
	
	// File Action Factories - Make a file action type.
	/*****************************************************************************
	 * Produces a  file action.												     *
	 *****************************************************************************/
	public static MenuAction makeFileMenuAction(ActionType type, UMLWindow win) 
		throws Exception
	{
		if (!type.isFileAction) {
			throw new Exception("Factory: Type is not a file action.");
		}
		switch(type) {
		case NEW:       	return new New(win);
		case OPEN:        	return new Open(win);
		case SAVE: 			return new Save(win); 
		case SAVE_AS: 		return new SaveAs(win); 
		case EXIT:  		return new Exit();
		default:           	return null;
		}
	}
	
	// Edit action factory - Make an edit action type.
	/*****************************************************************************
	 * Produces an edit action												     *
	 *****************************************************************************/
	public static MenuAction makeEditMenuAction(ActionType type, UMLWindow win)
		throws Exception {
		if (type.isFileAction) {
			throw new Exception("Factory: Type is not an edit action.");
		}
		switch(type) {
		case UNDO:			return new Undo(win);
		case REDO:     		return new Redo(win);
		case SELECT:	 	return new Select(win);
		case DELETE: 		return new Delete(win);
		default:           	return null;
		}
	}
}
 
