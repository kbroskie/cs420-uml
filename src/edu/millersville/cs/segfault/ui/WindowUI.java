package edu.millersville.cs.segfault.ui;

import edu.millersville.cs.segfault.model.UMLModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class WindowUI extends JPanel 
			implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JFrame winFrame;
	private WindowController winController;
	private UMLPanel umlPanel;
	
	private static final String fileMenuText = "File";
	private static final String newMenuText = "New";
	private static final String openMenuText = "Open";
	private static final String saveMenuText = "Save";
	private static final String exitMenuText = "Exit";	
	private static final String editMenuText = "Edit";
	private static final String undoMenuText = "Undo";
	private static final String redoMenuText = "Redo";

	
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
	 * Builds the File menu and sets the hotkeys.
	 * @return returns the file submenu
	 */
	private JMenu buildFileMenu() { 
	   // Create the menu items.
	   JMenu fileMenu = new JMenu(fileMenuText);
	   JMenuItem newItem = new JMenuItem(newMenuText);
	   JMenuItem openItem = new JMenuItem(openMenuText);
	   JMenuItem saveItem = new JMenuItem(saveMenuText);
	   JMenuItem exitItem = new JMenuItem(exitMenuText);

	   // Add an action listener for the exit option.
	   exitItem.addActionListener(new ExitListener());

	   // Set the hotkeys for the sub-menu options.
	   fileMenu.setMnemonic(KeyEvent.VK_F);
	   newItem.setMnemonic(KeyEvent.VK_N);
	   openItem.setMnemonic(KeyEvent.VK_O);
	   saveItem.setMnemonic(KeyEvent.VK_S);
	   exitItem.setMnemonic(KeyEvent.VK_X);
	      
	   // Add the sub-menus to the File menu.
	   fileMenu.add(newItem);
	   fileMenu.add(openItem);
	   fileMenu.add(saveItem);
	   fileMenu.add(exitItem);
	   
	   return fileMenu;
	}

	/*
	 * Builds the edit sub-menu.
	 * @return returns the edit submenu
	 */
	private JMenu buildEditMenu() {
	   // Create the menu items.
	   JMenu editMenu = new JMenu(editMenuText);
	   JMenuItem undoItem = new JMenuItem(undoMenuText);
	   JMenuItem redoItem = new JMenuItem(redoMenuText);
	   
	   // Set the hotkeys.
	   editMenu.setMnemonic(KeyEvent.VK_E);
	   undoItem.setMnemonic(KeyEvent.VK_Z);
	   redoItem.setMnemonic(KeyEvent.VK_Y);

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
	   JPanel optionsPanel = new JPanel(); 
	   optionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
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
			 // Call to puzzles open method.
		 }
		 else if (selectedCommand == saveMenuText) {
			 // Call to puzzles save method.
		 }
		 else if (selectedCommand == undoMenuText) {
			 umlPanel.undo();
		 }
		 else if (selectedCommand == redoMenuText) {
			 umlPanel.redo();
		 }
	 } 	 
}
