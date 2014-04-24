package edu.millersville.cs.segfault.ui.menu;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import edu.millersville.cs.segfault.ui.UMLWindow;

/**************************************************************************
 * A menu for enabling and disabling subviews and the toolbar in the main 
 * window.
 * @author Kimberlyn Broskie, Daniel Rabiega
 *************************************************************************/
public class OptionsMenu extends JMenu {
	
	//*************************************************************************
	// Static Instance Variables

	private static final long serialVersionUID = 4310696522154979212L;
	private static final String hideToolbarText = "Show Toolbar";

	//*************************************************************************
	// Instance Variables

	private UMLWindow parent;
	private JCheckBox toolbarCheckBox;
	
	/**************************************************************************
	 * Builds an OptionsMenu and initializes it with a reference to its parent.
	 * @param parent the frame for the interface
	 *************************************************************************/
	public OptionsMenu(UMLWindow parent) {
		super("Options");
		this.parent = parent;
		
		Action newAction;
		JMenuItem newItem;
		
		// Create and add options menu items.
		for (ActionType type : ActionType.optionsMenuTypeList()) {
			// Add a menu separator to group similar actions.
			if (type.isDifferentActionGroup) {
				addSeparator();
			 }
			try {
				   newAction = (Action)ActionType.makeOptionMenuAction(type, parent);
				   newItem = new JMenuItem(newAction);
				   add (newItem); 
			} catch (Exception e) {}
		}
		
		addSeparator();
		
		// Create a checkbox to show/hide the toolbar.
		toolbarCheckBox = new JCheckBox(hideToolbarText);
		toolbarCheckBox.setSelected(true);
		toolbarCheckBox.addItemListener(new ToolbarCheckBoxListener());
		add(toolbarCheckBox);		
	}
	
	//*************************************************************************
	// Action Listeners
	
	// Handles the events generated by the user clicking a CheckBox.
	private class ToolbarCheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (toolbarCheckBox.isSelected()) {
				parent.getToolbar().setVisible(true);
			}
			else {
				parent.getToolbar().setVisible(false);
	        }
	    }
	}
}
