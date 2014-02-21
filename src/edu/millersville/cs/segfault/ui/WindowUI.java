package edu.millersville.cs.segfault.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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


import edu.millersville.cs.segfault.model.UMLModel;

public class WindowUI extends JPanel 
			implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JFrame winFrame;
	private WindowController winController;
	private UMLPanel umlPanel;
	
	// Menu Items
	private static final String fileMenuText = "File";
	private static final String newMenuText = "New";
	private static final String openMenuText = "Open";
	private static final String saveMenuText = "Save";
	private static final String saveAsMenuText = "Save as...";
	private static final String exitMenuText = "Exit";	
	private static final String editMenuText = "Edit";
	private static final String undoMenuText = "Undo";
	private static final String redoMenuText = "Redo";
	
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

	   // Add the sub-menus to the Edit menu.
	   editMenu.add(undoItem);
	   editMenu.add(redoItem);
	   
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
		
	   GridBagLayout gridBag = new GridBagLayout();
	   JPanel optionsPanel = new JPanel();
	   optionsPanel.setLayout(gridBag);
	   
	   GridBagConstraints optionsConstraints = new GridBagConstraints();
	   optionsConstraints.fill = GridBagConstraints.HORIZONTAL;
	   //optionsConstraints.anchor = GridBagConstraints.PAGE_START;
	   optionsConstraints.gridx = 0;
	   optionsConstraints.gridy = 0;
	   
	   optionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
	   
	   JButton objectButton = new JButton(optionsPaneObjectDraw);
	   objectButton.addActionListener(this);
	   gridBag.setConstraints(objectButton, optionsConstraints);
	   optionsPanel.add(objectButton);
	   
	   
	   ++optionsConstraints.gridy;
	   JButton relationButton = new JButton(optionsPaneRelationDraw);
	   relationButton.addActionListener(this);
	   gridBag.setConstraints(relationButton, optionsConstraints);
	   optionsPanel.add(relationButton);
	   
	   ++optionsConstraints.gridy;
	   JButton selectionButton = new JButton(optionsPaneSelect);
	   selectionButton.addActionListener(this);
	   gridBag.setConstraints(selectionButton, optionsConstraints);
	   optionsPanel.add(selectionButton);
	   
	   ++optionsConstraints.gridy;
	   optionsConstraints.weighty = 1.0;
	   JPanel emptySpace = new JPanel();
	   gridBag.setConstraints(emptySpace, optionsConstraints);
	   optionsPanel.add(emptySpace);
	   
	   wFrame.add(optionsPanel, BorderLayout.WEST);
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
		 } else if (selectedCommand == optionsPaneObjectDraw ) {
			 umlPanel.changeInteractionMode(new DrawObjectMode(umlPanel));
		 } else if (selectedCommand == optionsPaneRelationDraw ) {
			 umlPanel.changeInteractionMode(new DrawRelationMode(umlPanel));
		 } else if (selectedCommand == optionsPaneSelect ) {
			 umlPanel.changeInteractionMode(new SelectionMode(umlPanel));
		 }
	 } 	 
}
