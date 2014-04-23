package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import edu.millersville.cs.segfault.ui.menu.ActionType;

public class Toolbar extends JToolBar {

	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************

	private static final long serialVersionUID = -8304808920676331117L;

	private static final Color BUTTON_COLOR = new Color(207,219,230);

	// Dimensions for the toolbar & buttons.
	private static final Dimension TOOLBOX_MIN_SIZE = new Dimension(840, 36);
	private static final Dimension TOOLBAR_MIN_SIZE = new Dimension(840, 39);
	private static final Dimension TOOLBOX_MAX_SIZE = new Dimension(1140, 36);
	private static final Dimension TOOLBAR_MAX_SIZE = new Dimension(840, 39);
	private static final Dimension BUTTON_SIZE = new Dimension(38, 38);

	private static final String [] toolbarActions = 
		{"New", "Open", "Save", "Undo", "Redo","Cut", "Copy", "Paste",
		 "Delete", "Snap to Grid", "Show/Hide Grid", "Help"};


	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	private UMLWindow parentWindow;

	//*************************************************************************
	// Constructors	
	//*************************************************************************

	/**************************************************************************
	 * Builds a toolbar to hold the menu options a user can select.
	 * @param parent the panel for the current UML model.
	 *************************************************************************/
	public Toolbar(UMLWindow parent) {
		super();
		parentWindow = parent;	

		setFloatable(false);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		setMinimumSize(TOOLBAR_MIN_SIZE);
		setPreferredSize(TOOLBAR_MIN_SIZE);
		setMaximumSize(TOOLBAR_MAX_SIZE);
		setBackground(BUTTON_COLOR);

		// Create a box to group the buttons together.
		Box toolbarBox = Box.createHorizontalBox();
		toolbarBox.setMinimumSize(TOOLBOX_MIN_SIZE);
		toolbarBox.setPreferredSize(TOOLBOX_MIN_SIZE);
		toolbarBox.setMaximumSize(TOOLBOX_MAX_SIZE);
		toolbarBox.setBackground(BUTTON_COLOR);

		// Build and add the buttons.
		for (String action : toolbarActions) {
			toolbarBox.add(createButton(action));
			if (action == toolbarActions[2] || action == toolbarActions[4] ||
				action == toolbarActions[8])
				toolbarBox.add(Box.createHorizontalGlue());
		}

		add(toolbarBox);
	}


	//*************************************************************************
	// Mutators
	//*************************************************************************

	/**************************************************************************
	 * Creates a button for the given string, adding icons and action commands.
	 * @param action the name of the button to create
	 *************************************************************************/
	public JButton createButton(String action) {
		ImageIcon newImage = new ImageIcon("img/32/" + 
				action.replaceAll(" ","").replaceAll("/","").toLowerCase() + ".png");
		JButton newButton = new JButton();

		// Use an action from the factory if it exists.
		try {
			Action newAction = (Action)ActionType.makeFileMenuAction(
								ActionType.valueOf(action.toUpperCase()), parentWindow);
			newButton.setAction(newAction);	
			newButton.setText(null);
		}
		catch (Exception ex) {
			newButton.setActionCommand(action);
		}
		finally {
			newButton.setIcon(newImage);
		}
		
		newButton.setToolTipText(action);
		newButton.setMinimumSize(BUTTON_SIZE);
		newButton.setMaximumSize(BUTTON_SIZE);
		newButton.setPreferredSize(BUTTON_SIZE);
		newButton.setBackground(BUTTON_COLOR);
		newButton.setBorderPainted(false);
		newButton.setFocusPainted(false);

		return newButton;
	}
}