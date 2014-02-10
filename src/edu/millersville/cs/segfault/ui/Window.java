package edu.millersville.cs.segfault.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel optionsPanel;
	private JPanel umlPanel;

	private JMenuBar menuBar;

	private JMenu fileMenu;
	private JMenu editMenu;

	private JMenuItem newItem;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem exitItem;
	private JMenuItem undoItem;
	private JMenuItem redoItem;

	public Window() {
	    setTitle("UML Editor");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new BorderLayout());

	    buildMenuBar();  
	    buildOptionsPanel();
	    buildUMLPanel();
	    
	    add(optionsPanel, BorderLayout.WEST);
	    add(umlPanel, BorderLayout.CENTER);
	    
	    pack();
	    setVisible(true);	
	}

	/*
	 * Builds the menu bar.
	 */
	private void buildMenuBar() {
	   menuBar = new JMenuBar();
	   
	   // Create the menus.
	   buildFileMenu();
	   buildEditMenu();
	   
	   // Add the menus to the menu bar.
	   menuBar.add(fileMenu);
	   menuBar.add(editMenu);
	   
	   // Set the window's menu bar.
	   setJMenuBar(menuBar);
	}

	/**
	 * The buildFileMenu method builds the File menu.
	 */
	private void buildFileMenu() { 
	   // Create the menu items.
	   fileMenu = new JMenu("File");
	   newItem = new JMenuItem("New");
	   openItem = new JMenuItem("Open");
	   saveItem = new JMenuItem("Save");
	   exitItem = new JMenuItem("Exit");

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
	}

	/*
	 * Build the edit sub-menu.
	 */
	private void buildEditMenu() {
	   // Create the menu items.
	   editMenu = new JMenu("Edit");
	   undoItem = new JMenuItem("Undo");
	   redoItem = new JMenuItem("Redo");
	   
	   // Set the hotkeys.
	   editMenu.setMnemonic(KeyEvent.VK_E);
	   undoItem.setMnemonic(KeyEvent.VK_Z);
	   exitItem.setMnemonic(KeyEvent.VK_Y);

	   // Add the sub-menus to the Edit menu.
	   editMenu.add(undoItem);
	   editMenu.add(redoItem);
	}

	/**
	 * Panel to place the various UML object selections.
	 */
	private void buildOptionsPanel() {
	   optionsPanel = new JPanel();
	   optionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
	} 

	/**
	 * Adds the UML panel.
	 */
	private void buildUMLPanel() {
		umlPanel = new UMLPanel();
		umlPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	 }

	/**
	 * Handles the event generated when the user selects 
	 * the exit option from the File menu.
	 */
	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	/*
	 * Display the UML Editor.
	 */
	public static void main(String[] args) {
	   new Window();
	}
}
