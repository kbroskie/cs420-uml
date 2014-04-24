package edu.millersville.cs.segfault.ui.menu;

import java.util.LinkedList;
import java.util.Vector;

import javax.swing.ImageIcon;

import edu.millersville.cs.segfault.ui.UMLWindow;
import edu.millersville.cs.segfault.ui.menu.actions.Copy;
import edu.millersville.cs.segfault.ui.menu.actions.Cut;
import edu.millersville.cs.segfault.ui.menu.actions.Delete;
import edu.millersville.cs.segfault.ui.menu.actions.Exit;
import edu.millersville.cs.segfault.ui.menu.actions.New;
import edu.millersville.cs.segfault.ui.menu.actions.Open;
import edu.millersville.cs.segfault.ui.menu.actions.Paste;
import edu.millersville.cs.segfault.ui.menu.actions.Redo;
import edu.millersville.cs.segfault.ui.menu.actions.Save;
import edu.millersville.cs.segfault.ui.menu.actions.SaveAs;
import edu.millersville.cs.segfault.ui.menu.actions.SaveImage;
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
	SAVE_IMAGE	(true, false),
	HELP        (false, true),
	EXIT  		(true, true),
	UNDO		(false, false),
	REDO        (false, false),
	SELECT	 	(false, true),
	DELETE 		(false, false),
	CUT			(false, true),
	COPY		(false, false),
	PASTE		(false, false);
	
	// Determine whether the variable is a file or edit action type.
	public final boolean      isFileAction;
	
	// Determine whether the variable is the start of a similar group.
	public final boolean 	  isDifferentActionGroup;
	
	public final ImageIcon    icon; 
	
	private static final Vector<MenuAction> actions = new Vector<MenuAction>(9);
	
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
	 * Retrieves an existing filing action or produces one if it does not exist	 *
	 *****************************************************************************/
	public static MenuAction makeFileMenuAction(ActionType type, UMLWindow win) 
		throws Exception
	{
		if (!type.isFileAction) {
			throw new Exception("Factory: Type is not a file action.");
		}
		
		MenuAction newAction;
		
		// Create a new instance if the type does not exist.
	    if (!actions.contains(type)) {
	    	switch(type) {
			case NEW:       	newAction = new New(win);
								break;
			case OPEN:        	newAction = new Open(win);
								break;
			case SAVE: 			newAction = new Save(win); 
								break;
			case SAVE_AS: 		newAction = new SaveAs(win); 
								break;
			case SAVE_IMAGE:	newAction = new SaveImage(win);
								break;
			case EXIT:  		newAction = new Exit();
								break;
			default:           	newAction = null;
			
	    	}
		    actions.add(newAction);
	    }
	    
	    // Retrieve the existing action.
	    else {
	    	newAction = actions.get(actions.indexOf(type));
	    }
	    return newAction;		
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
		case CUT:			return new Cut(win);
		case PASTE:			return new Paste(win);
		case COPY:			return new Copy(win);
		default:           	return null;
		}
	}
}
 
