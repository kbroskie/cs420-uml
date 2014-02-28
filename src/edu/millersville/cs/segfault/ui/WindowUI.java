package edu.millersville.cs.segfault.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


import edu.millersville.cs.segfault.model.DrawableType;
import javax.swing.border.TitledBorder;
import edu.millersville.cs.segfault.model.UMLModel;

public class WindowUI extends JPanel 
			implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JFrame winFrame;
	private WindowController winController;
	private UMLPanel umlPanel;
	
	// Menu Items
	/**** FILE */
	private static final String fileMenuText = "File";
	private static final String newMenuText = "New";
	private static final String openMenuText = "Open";
	private static final String saveMenuText = "Save";
	private static final String saveAsMenuText = "Save as...";
	private static final String exitMenuText = "Exit";
	
	/**** EDIT */
	private static final String editMenuText = "Edit";
	private static final String undoMenuText = "Undo";
	private static final String redoMenuText = "Redo";
	private static final String selectAllText = "Select/Deselect All";
	private static final String deleteText = "Delete";
	
	// Options Pane Items
	private static final String optionsPaneObjectDraw = "Object";
	private static final String optionsPaneRelationDraw = "Relation";
	private static final String optionsPaneSelect = "Select";

	
	/**
	 * WindowUI constructor that constructs a window
	 * and adds the basic components.
	 */
	public WindowUI (WindowController wController, JFrame wFrame) {
		winFrame = wFrame;
		winController = wController;
		JPanel mainUIPanel = setMainPanelLayout();
		winFrame.getContentPane().add(mainUIPanel, BorderLayout.CENTER);
		buildMenu(winFrame);
		buildUMLPanel(winFrame);
		buildOptionsPanel(winFrame);
	}
	
	/** 
	 * Returns the current UML model.
	 * @return the current UML model
	 */
	public UMLPanel uml () {
			return winController.uml();
	}
	
	/**
	 * Sets the layout for the main interface.
	 * @return returns the JPanel that holds all UI components.
	 */
	private JPanel setMainPanelLayout() {
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(this);	
	    setLayout(new BorderLayout());
	    return mainPanel;
	}
		
	/*
	 * Builds the menu bar.
	 * @param wFrame the frame for the interface
	 */
	private void buildMenu(JFrame wFrame) {
	   JMenuBar menuBar = new JMenuBar();
	   
	   // Create the sub-menus.
	   JMenu fileSubmenu = buildFileMenu();
	   JMenu editSubmenu = buildEditMenu();
	   
	   // Add the menus to the menu bar.
	   menuBar.add(fileSubmenu);
	   menuBar.add(editSubmenu);
	   
	   // Set the window's menu bar.
	   wFrame.setJMenuBar(menuBar);
	}
	
	/**
	 * Builds the File menu.
	 * @return returns the file submenu
	 */
	private JMenu buildFileMenu() { 
	   // Create the menu items, adding action listeners and accelerators.
	   JMenu fileMenu = new JMenu(fileMenuText);
	   
	   JMenuItem newItem = new JMenuItem(newMenuText);
	   newItem.addActionListener(this);
	   newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
	   
	   JMenuItem openItem = new JMenuItem(openMenuText);
	   openItem.addActionListener(this);
	   openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));

	   JMenuItem saveItem = new JMenuItem(saveMenuText);
	   saveItem.addActionListener(this);
	   saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
	   
	   JMenuItem saveAsItem = new JMenuItem(saveAsMenuText);
	   saveAsItem.addActionListener(this);

	   JMenuItem exitItem = new JMenuItem(exitMenuText);
	   exitItem.addActionListener(new ExitListener());
	   exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));

	   // Add the sub-menus to the File menu.
	   fileMenu.add(newItem);
	   fileMenu.add(openItem);
	   fileMenu.add(saveItem);
	   fileMenu.add(saveAsItem);
	   fileMenu.add(exitItem);
	   
	   return fileMenu;
	}

	/*
	 * Builds the edit sub-menu.
	 * @return returns the edit submenu
	 */
	private JMenu buildEditMenu() {
	   // Create the edit submenu items, and add accelerators.
	   JMenu editMenu = new JMenu(editMenuText);
	   
	   JMenuItem undoItem = new JMenuItem(undoMenuText);
	   undoItem.addActionListener(this);
	   undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));

	   JMenuItem redoItem = new JMenuItem(redoMenuText);
	   redoItem.addActionListener(this);
	   redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK));
	   
	   JMenuItem selectAllItem = new JMenuItem(selectAllText);
	   selectAllItem.addActionListener(this);
	   selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
	   
	   JMenuItem deleteItem = new JMenuItem(deleteText);
	   deleteItem.addActionListener(this);
	   deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
	   
	   // Add the sub-menus to the Edit menu.
	   editMenu.add(undoItem);
	   editMenu.add(redoItem);
	   editMenu.add(selectAllItem);
	   editMenu.add(deleteItem);
	   
	   return editMenu;
	}
	
	/**
	 * Builds the UML panel.
	 * @param wFrame the frame for the interface
	 */
	private void buildUMLPanel(JFrame wFrame) {
		umlPanel = new UMLPanel();
		umlPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		wFrame.add(umlPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Creates a panel to place the various UML object selections.
	 * @param wFrame the frame for the interface
	 */
	private void buildOptionsPanel(JFrame wFrame) {
		
	   // Create the main panel and set the layout.
	   GridBagLayout gridBag = new GridBagLayout();
	   JPanel optionsPanel = new JPanel();
	   optionsPanel.setLayout(gridBag);
	   optionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
	   
	   // Set the gridbag options for the options panel.
	   GridBagConstraints optionsConstraints = new GridBagConstraints();
	   optionsConstraints.fill = GridBagConstraints.HORIZONTAL;
	   optionsConstraints.anchor = GridBagConstraints.PAGE_START;
	   optionsConstraints.gridx = 0;
	   optionsConstraints.gridy = 0;
	   optionsConstraints.weighty = 0.5;
	   optionsConstraints.insets = new Insets(2,0,0,0);
	   	   
	   // Build and add the objects panel
	   JPanel objectsPanel = new JPanel();
	   buildObjectsPanel(objectsPanel);
	   gridBag.setConstraints(objectsPanel, optionsConstraints);
	   optionsPanel.add(objectsPanel);   

	   // Build and add the relations panel.
	   ++optionsConstraints.gridy;
	   JPanel relationsPanel = new JPanel();
	   buildRelationsPanel(relationsPanel);  
	   gridBag.setConstraints(relationsPanel, optionsConstraints);	  
	   optionsPanel.add(relationsPanel);
	   
	   // Set the options so the last item appears on the bottom of the panel.
	   ++optionsConstraints.gridy;
	   optionsConstraints.weighty = 1.0;
	   optionsConstraints.anchor = GridBagConstraints.PAGE_END;
	   optionsConstraints.insets = new Insets(2,5,3,5);

	   // Build and add the select button.
	   JButton selectionButton = new JButton(optionsPaneSelect);
	   selectionButton.addActionListener(this);
	   gridBag.setConstraints(selectionButton, optionsConstraints);
	   optionsPanel.add(selectionButton);
	   
	   // Add the optionsPanel to the main frame.
	   wFrame.add(optionsPanel, BorderLayout.WEST);
	} 
	
	/**
	 * Builds a panel to hold all the object options for the main options panel.
	 * @param objectsPanel Holds all the object options a user can select.
	 */
	private void buildObjectsPanel(JPanel objectsPanel) {
		// Set the panel layout and title.
		objectsPanel.setLayout(new GridLayout(7,1));
		objectsPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(), "Objects", 
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, 
				new Font("SansSerif", Font.PLAIN, 15), Color.BLACK));
		
		// Create the object buttons and action listeners.
		JButton objectButton = new JButton(optionsPaneObjectDraw);
		objectButton.addActionListener(this);
		objectsPanel.add(objectButton, BorderLayout.CENTER);		
	}
	
	/**
	 * Builds a panel to hold all the relations options for the main options panel.
	 * @param relationsPanel Holds all the relation options a user can select.
	 */
	private void buildRelationsPanel(JPanel relationsPanel) {
		// Set the panel layout and title.
		relationsPanel.setLayout(new GridLayout(7,1));
		relationsPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(), "Relations", 
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, 
				new Font("SansSerif", Font.PLAIN, 15), Color.BLACK));
		
		// Create the object buttons and action listeners.
		JButton relationButton = new JButton(optionsPaneRelationDraw);
		relationButton.addActionListener(this);
		relationsPanel.add(relationButton);
	}	
	
	/**
	 * Handles the event generated when the user selects 
	 * the exit option from the File menu.
	 * @param e the event information triggered by selecting the exit option
	 */
	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	/**
	 * Handles the events generated by the user selecting
	 * a menu option.
	 * @param se the selected event and source 
	 */
	 public void actionPerformed(ActionEvent se) {
		 String selectedCommand = se.getActionCommand();
		 	 	 
		 if (selectedCommand == newMenuText) {
			 umlPanel.changeModel(new UMLModel());
		 }
		 else if (selectedCommand == openMenuText) {
			 umlPanel.load();
		 }
		 else if (selectedCommand == saveMenuText) {
			 umlPanel.save(umlPanel.model().serialize());
		 }
		 else if (selectedCommand == saveAsMenuText) {
			 umlPanel.saveAs(umlPanel.model().serialize());
		 }
		 else if (selectedCommand == undoMenuText) {
			 umlPanel.undo();	
		 }
		 else if (selectedCommand == redoMenuText) {
			 umlPanel.redo();
		 }
		 else if (selectedCommand == selectAllText) {
			 umlPanel.select();
		 }
		 else if (selectedCommand == deleteText ) {
			 umlPanel.changeModel(umlPanel.model().deleteSelected());
		 } else if (selectedCommand == optionsPaneObjectDraw ) {
			 umlPanel.changeInteractionMode(new DrawMode(DrawableType.OBJECT, umlPanel));
		 } else if (selectedCommand == optionsPaneRelationDraw ) {
			 umlPanel.changeInteractionMode(new DrawMode(DrawableType.RELATION, umlPanel));
		 } else if (selectedCommand == optionsPaneSelect ) {
			 umlPanel.changeInteractionMode(new SelectionMode(umlPanel));
		 }
	 } 	 
}
