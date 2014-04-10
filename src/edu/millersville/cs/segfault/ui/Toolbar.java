package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import edu.millersville.cs.segfault.model.UMLModel;

public class Toolbar extends JToolBar
					implements ActionListener {

	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************
	
	private static final long serialVersionUID = -8304808920676331117L;
	
	private static final Color BUTTON_COLOR = new Color(207,219,230);

	
	// Dimensions for the toolbar & buttons.
	private static final Dimension TOOLBOX_MIN_SIZE = new Dimension(840, 36);
	private static final Dimension TOOLBAR_MIN_SIZE = new Dimension(840, 39);
	private static final Dimension TOOLBOX_MAX_SIZE = new Dimension(1140, 36);
	private static final Dimension TOOLBAR_MAX_SIZE = new Dimension(1140, 39);
	private static final Dimension BUTTON_SIZE = new Dimension(38, 38);

	// Toolbar items
	private static final String newAction = "New";
	private static final String loadAction = "Load";
	private static final String saveAction = "Save";
	
	private static final String undoAction = "Undo";
	private static final String redoAction = "Redo";
	private static final String cutAction = "Cut";
	private static final String copyAction = "Copy";
	private static final String pasteAction = "Paste";
	private static final String deleteAction = "Delete";
	
	private static final String snapAction = "Snap to Grid";
	private static final String gridAction = "Show/Hide Grid";
	
	private static final String helpAction = "Help";
	
	
	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	private UMLWindow parentWindow;
	
	//*************************************************************************
	// Constructors	
	//*************************************************************************
	
	/**************************************************************************
	 * Builds a toolbar to hold the menu options a user can select.
	 * @param umlPanel the panel for the current UML model.
	 *************************************************************************/
	public Toolbar(UMLWindow parent) {
		super();
		
		setFloatable(false);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		setMinimumSize(TOOLBAR_MIN_SIZE);
		setPreferredSize(TOOLBAR_MIN_SIZE);
		setMaximumSize(TOOLBAR_MAX_SIZE);
		setBackground(BUTTON_COLOR);
		
		parentWindow = parent;	
		
		// Build and add the new button.
		JButton newButton = new JButton(new ImageIcon("img/32/New.png"));
		newButton.setActionCommand(newAction);
		newButton.setToolTipText(newAction);
		newButton.setMinimumSize(BUTTON_SIZE);
		newButton.setMaximumSize(BUTTON_SIZE);
		newButton.setPreferredSize(BUTTON_SIZE);
		newButton.addActionListener(this);
		newButton.setBorderPainted(false);
		newButton.setFocusPainted(false);
		newButton.setBackground(BUTTON_COLOR);

		// Build and add the load button.
		JButton loadButton = new JButton(new ImageIcon("img/32/Load.png"));
		loadButton.setActionCommand(loadAction);
		loadButton.setToolTipText(loadAction);
		loadButton.setMinimumSize(BUTTON_SIZE);
		loadButton.setMaximumSize(BUTTON_SIZE);
		loadButton.setPreferredSize(BUTTON_SIZE);
		loadButton.addActionListener(this);
		loadButton.setBorderPainted(false);
		loadButton.setFocusPainted(false);
		loadButton.setBackground(BUTTON_COLOR);
	
		// Build and add the save button.
		JButton saveButton = new JButton(new ImageIcon("img/32/Save.png"));
		saveButton.setActionCommand(saveAction);
		saveButton.setToolTipText(saveAction);
		saveButton.setMinimumSize(BUTTON_SIZE);
		saveButton.setMaximumSize(BUTTON_SIZE);
		saveButton.setPreferredSize(BUTTON_SIZE);
		saveButton.addActionListener(this);
		saveButton.setBorderPainted(false);
		saveButton.setFocusPainted(false);
		saveButton.setBackground(BUTTON_COLOR);

		// Undo 
		JButton undoButton = new JButton(new ImageIcon("img/32/Undo.png"));
		undoButton.setActionCommand(undoAction);
		undoButton.setToolTipText(undoAction);
		undoButton.setMinimumSize(BUTTON_SIZE);
		undoButton.setMaximumSize(BUTTON_SIZE);
		undoButton.setPreferredSize(BUTTON_SIZE);
		undoButton.addActionListener(this);
		undoButton.setBorderPainted(false);
		undoButton.setFocusPainted(false);
		undoButton.setBackground(BUTTON_COLOR);
		
		// Redo
		JButton redoButton = new JButton(new ImageIcon("img/32/Redo.png"));
		redoButton.setActionCommand(redoAction);
		redoButton.setToolTipText(redoAction);
		redoButton.setMinimumSize(BUTTON_SIZE);
		redoButton.setMaximumSize(BUTTON_SIZE);
		redoButton.setPreferredSize(BUTTON_SIZE);
		redoButton.addActionListener(this);
		redoButton.setBorderPainted(false);
		redoButton.setFocusPainted(false);
		redoButton.setBackground(BUTTON_COLOR);

		// Cut 
		JButton cutButton = new JButton(new ImageIcon("img/32/cut.png"));
		//cutButton.setActionCommand(cutAction);
		cutButton.setToolTipText(cutAction);
		cutButton.setMinimumSize(BUTTON_SIZE);
		cutButton.setMaximumSize(BUTTON_SIZE);
		cutButton.setPreferredSize(BUTTON_SIZE);
		cutButton.addActionListener(this);
		cutButton.setBorderPainted(false);
		cutButton.setFocusPainted(false);
		cutButton.setBackground(BUTTON_COLOR);
		
		// Copy
		JButton copyButton = new JButton(new ImageIcon("img/32/Copy.png"));
		//copyButton.setActionCommand(copyAction);
		copyButton.setToolTipText(copyAction);
		copyButton.setPreferredSize(BUTTON_SIZE);
		copyButton.setMinimumSize(BUTTON_SIZE);
		copyButton.setMaximumSize(BUTTON_SIZE);
		copyButton.setPreferredSize(BUTTON_SIZE);
		copyButton.addActionListener(this);
		copyButton.setBorderPainted(false);
		copyButton.setFocusPainted(false);
		copyButton.setBackground(BUTTON_COLOR);
		
		// Paste
		JButton pasteButton = new JButton(new ImageIcon("img/32/Paste.png"));
		//pasteButton.setActionCommand(pasteAction);
		pasteButton.setToolTipText(pasteAction);
		pasteButton.setMinimumSize(BUTTON_SIZE);
		pasteButton.setMaximumSize(BUTTON_SIZE);
		pasteButton.setPreferredSize(BUTTON_SIZE);
		pasteButton.addActionListener(this);
		pasteButton.setBorderPainted(false);
		pasteButton.setFocusPainted(false);
		pasteButton.setBackground(BUTTON_COLOR);
		
		// Delete
		JButton deleteButton = new JButton(new ImageIcon("img/32/Destroy.png"));
		deleteButton.setActionCommand(deleteAction);
		deleteButton.setToolTipText(deleteAction);
		deleteButton.setMinimumSize(BUTTON_SIZE);
		deleteButton.setMaximumSize(BUTTON_SIZE);
		deleteButton.setPreferredSize(BUTTON_SIZE);
		deleteButton.addActionListener(this);
		deleteButton.setBorderPainted(false);
		deleteButton.setFocusPainted(false);
		deleteButton.setBackground(BUTTON_COLOR);
		
		// Snap
		JButton snapButton = new JButton(new ImageIcon("img/32/snap.png"));
		snapButton.setActionCommand(snapAction);
		snapButton.setToolTipText(snapAction);
		snapButton.setMinimumSize(BUTTON_SIZE);
		snapButton.setMaximumSize(BUTTON_SIZE);
		snapButton.setPreferredSize(BUTTON_SIZE);
		snapButton.addActionListener(this);
		snapButton.setBorderPainted(false);
		snapButton.setFocusPainted(false);
		snapButton.setBackground(BUTTON_COLOR);
		
		//Grid
		JButton gridButton = new JButton(new ImageIcon("img/32/grid.png"));
		//gridButton.setActionCommand(gridAction);
		gridButton.setToolTipText(gridAction);
		gridButton.setMinimumSize(BUTTON_SIZE);
		gridButton.setMaximumSize(BUTTON_SIZE);
		gridButton.setPreferredSize(BUTTON_SIZE);
		gridButton.addActionListener(this);
		gridButton.setBorderPainted(false);
		gridButton.setFocusPainted(false);
		gridButton.setBackground(BUTTON_COLOR);

		// Help
		JButton helpButton = new JButton(new ImageIcon("img/32/Help.png"));
		//helpButton.setActionCommand(helpAction);
		helpButton.setToolTipText(helpAction);
		helpButton.setMinimumSize(BUTTON_SIZE);
		helpButton.setMaximumSize(BUTTON_SIZE);
		helpButton.setPreferredSize(BUTTON_SIZE);
		helpButton.addActionListener(this);
		helpButton.setBorderPainted(false);
		helpButton.setFocusPainted(false);
		helpButton.setBackground(BUTTON_COLOR);

		// Create a box to group the buttons together.
		Box toolbarBox = Box.createHorizontalBox();
		toolbarBox.setMinimumSize(TOOLBOX_MIN_SIZE);
		toolbarBox.setPreferredSize(TOOLBOX_MIN_SIZE);
		toolbarBox.setMaximumSize(TOOLBOX_MAX_SIZE);
		toolbarBox.setBackground(BUTTON_COLOR);
	
		toolbarBox.add(newButton);
		toolbarBox.add(loadButton);
		toolbarBox.add(saveButton);
		toolbarBox.add(Box.createHorizontalGlue());
		
		toolbarBox.add(undoButton);
		toolbarBox.add(redoButton);
		toolbarBox.add(Box.createHorizontalGlue());
		
		toolbarBox.add(cutButton);
		toolbarBox.add(copyButton);
		toolbarBox.add(deleteButton);
		toolbarBox.add(Box.createHorizontalGlue());
		
		toolbarBox.add(snapButton);
		toolbarBox.add(gridButton);
		toolbarBox.add(Box.createHorizontalGlue());
		
		toolbarBox.add(helpButton);	
		
		add(toolbarBox);
	}

	
	//*************************************************************************
	// Event Listeners
	//*************************************************************************
	 
	/**************************************************************************
	 * Handles the events generated by the user selecting
	 * an option.
	 * @param se the selected event and source 
	 *************************************************************************/
	public void actionPerformed(ActionEvent se) {

		Object selectedCommand = se.getActionCommand();
		
		 if (selectedCommand == newAction) {
			 parentWindow.getUMLPanel().changeModel(new UMLModel());
		 }
		 else if (selectedCommand == loadAction) {
			 parentWindow.getUMLPanel().load();
		 }
		 else if (selectedCommand == saveAction) {
			 parentWindow.getUMLPanel().save(
					 parentWindow.getUMLPanel().getModel().serialize());
		 }
		 else if (selectedCommand == undoAction) {
		 		parentWindow.getUMLPanel().undo();	
		 }
		else if (selectedCommand == redoAction) {
			parentWindow.getUMLPanel().redo();
		} 
		else if (selectedCommand == deleteAction) {
			try {
					parentWindow.getUMLPanel().changeModel(
							parentWindow.getUMLPanel().getModel().deleteSelected());
				} catch (Exception e) {}
		}
	} 	
}
