package edu.millersville.cs.segfault.ui.menu;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import edu.millersville.cs.segfault.ui.UMLWindow;

/**************************************************************************
 * UMLEditMenu is the class responsible for instantiating
 * the Edit submenu and handling the user selections for the submenu.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class UMLEditMenu extends JMenu
						implements ActionListener {
	
	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = -3884476289367851522L;
	
	// EDIT MENU VARIABLES
	private static final String editMenuText = "Edit";
	private static final String undoMenuText = "Undo";
	private static final String redoMenuText = "Redo";
	private static final String selectAllText = "Select/Deselect All";
	
	//*************************************************************************
	// Constructors	
	//*************************************************************************
		
	/**************************************************************************
	* Constructor that builds the Edit submenu, with each submenu option
	* having a key accelerator.
	* @param wFrame the frame for the interface.
	* @param umlPanel the panel for the current UML model.
	*************************************************************************/
	public UMLEditMenu() {
	   super(editMenuText);
	   
	   // Create the undo menu item and add an accelerator.
	   JMenuItem undoItem = new JMenuItem(undoMenuText);
	   undoItem.addActionListener(this);
	   undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));

	   // Create the redo menu item and add an action listener and accelerator.	  
	   JMenuItem redoItem = new JMenuItem(redoMenuText);
	   redoItem.addActionListener(this);
	   redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK));
	   
	   // Create the select all menu item and add an action listener and accelerator.	  
	   JMenuItem selectAllItem = new JMenuItem(selectAllText);
	   selectAllItem.addActionListener(this);
	   selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
	   
	   // Add the sub-menus to the Edit menu.
	   add(undoItem);
	   add(redoItem);
	   add(selectAllItem);
	}
	
	
	//*************************************************************************
	// Event Listeners
	//*************************************************************************
	
	/**************************************************************************
	 * Handles the events generated by the user selecting
	 * an Edit submenu option.
	 * @param se the selected event and source 
	 *************************************************************************/
	 public void actionPerformed(ActionEvent se) {
		 String selectedCommand = se.getActionCommand();
		 
	 	 if (selectedCommand == undoMenuText) {
			 UMLWindow.getUMLPanel().undo();	
		 }
		 else if (selectedCommand == redoMenuText) {
			 UMLWindow.getUMLPanel().redo();
		 } 
	}
}