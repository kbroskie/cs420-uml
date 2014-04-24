package edu.millersville.cs.segfault.ui;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import edu.millersville.cs.segfault.model.DrawableType;

/**************************************************************************
 * OptionsPanel is the class responsible for creating a panel containing 
 * the different objects and relations a user can select.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class OptionsPanel extends JPanel
							implements ActionListener {

	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = -9118912567292664779L;
	
	private static final Color BUTTON_COLOR = new Color(231,237,243);
	
	// Non-Drawable Action Commands
	private static final String selectAction = "SELECT";
	private static final String textAction = "TEXT";
	
	// Dimensions for the panel and buttons.
	private static final Dimension OPTIONS_PANE_MAX_SIZE = new Dimension(130, 520);
	private static final Dimension OPTIONS_PANE_MIN_SIZE = new Dimension(130, 420);
	private static final Dimension BUTTON_SIZE = new Dimension(64, 64);
	private static final Dimension MAX_BOX_SIZE = new Dimension(130, 100);
	private static final Dimension MIN_BOX_SIZE = new Dimension(130, 80);

	
	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	private UMLWindow parentWindow;
	
	//*************************************************************************
	// Constructors	
	//*************************************************************************
	
	/**************************************************************************
	 * Builds a panel to hold the options for various objects and 
	 * relations that a user can select.
	 * @param parent the frame for the current UML model.
	 *************************************************************************/
	 public OptionsPanel (UMLWindow parent) {
		 super();
		 
		 parentWindow = parent;

		 setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		 setPreferredSize(OPTIONS_PANE_MAX_SIZE);
		 setMaximumSize(OPTIONS_PANE_MAX_SIZE);
		 setMinimumSize(OPTIONS_PANE_MIN_SIZE);	 
		 setBackground(BUTTON_COLOR);
		 setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, parent.getBackground()));
		 
		 // Variables to group and store the created buttons.
		 Vector<Box> boxGroup = new Vector<Box>(10);
		 ButtonGroup optionsButtonGroup = new ButtonGroup();
		 Box horizontalBox = createHorizontalBox();
		 int buttonCount = 0;
		 
		 // Create and add the select button.
		 JToggleButton newButton = createSelectButton();
		 optionsButtonGroup.add(newButton);
		 horizontalBox = addNewButton(horizontalBox, newButton, buttonCount);
		 ++buttonCount;
		 
		 // Create and add the text button.
		 newButton = createTextButton();
		 optionsButtonGroup.add(newButton);
		 horizontalBox = addNewButton(horizontalBox, newButton, buttonCount);
		 ++buttonCount;
			
		 // Add the objects and relation buttons to the button group 
		 // and boxGroup in pairs of two.
		 for (DrawableType type : DrawableType.typeList()) {
			 newButton = createButton(type.icon, type.selectedIcon, type.name());
			 optionsButtonGroup.add(newButton);
			 boxGroup = addBoxToGroupIfFullRow(horizontalBox, boxGroup, buttonCount);
			 horizontalBox = addNewButton(horizontalBox, newButton, buttonCount);
			 
			 ++buttonCount;	
		 }
		
		boxGroup.add(formatLastRow(horizontalBox, buttonCount));
		 
		// Add the button rows to the panel.
		for (Object b : boxGroup) {
			this.add((Component) b);
		}
	}	
	 
	//*************************************************************************
	// Mutators
	//*************************************************************************
	 
	 /**************************************************************************
	  * Creates a button for the given string, adding icons and default 
	  * properties.
	  * @param icon the image for the button
	  * @param selectedIcon the image for the button when selected
	  * @param name the action type
	  *************************************************************************/
	 private JToggleButton createButton(ImageIcon icon, ImageIcon selectedIcon, String name) {	
		JToggleButton newButton = new JToggleButton(icon);
			 
		newButton.setSelectedIcon(selectedIcon);
		newButton.setToolTipText(formatAction(name));
		newButton.setActionCommand(name);
			 
		newButton.setMaximumSize(BUTTON_SIZE);
		newButton.setPreferredSize(BUTTON_SIZE);
		newButton.setBackground(BUTTON_COLOR);
			 
		newButton.addActionListener(this);
		newButton.setBorderPainted(false);
		newButton.setFocusPainted(false);
			 		
		return newButton;
	}


	/**************************************************************************
	 * Creates a select button, giving it the initial focus.
	 *************************************************************************/
	private JToggleButton createSelectButton() {
		 JToggleButton newButton = 
				 createButton(new ImageIcon("img/64/selectionMode.png"), 
				 			  new ImageIcon("img/64/down/selectionMode.png"), selectAction);
		 
		 newButton.setActionCommand(selectAction);
		 newButton.addActionListener(this);
		 newButton.setSelected(true);
		 
		 return newButton;
	}
	
	/**************************************************************************
	 * Creates a text button.
	 *************************************************************************/
	private JToggleButton createTextButton() {
		 JToggleButton newButton = 
				 createButton(new ImageIcon("img/64/textMode.png"), 
				 			  new ImageIcon("img/64/down/textMode.png"), textAction);
		 
		 newButton.setActionCommand(textAction);
		 newButton.addActionListener(this);
		 
		 return newButton;
	}
	
	/**************************************************************************
	 * Formats a string, adding capitalization and spaces.
	 * @param name the string to format
	 *************************************************************************/
	private String formatAction(String name) {		
	
		if (name == null || name == "") {
			return name;
		}
		
		int separatorIndex = name.indexOf("_");
			 
		// Replace an underscore with a space, and leave the first letter 
		// of each word capitalized.
		if (separatorIndex > 0)
		{
			return name.substring(0,1) + 
				   name.substring(1, separatorIndex + 1).toLowerCase().replace("_", " ") +
				   name.substring(separatorIndex + 1, separatorIndex + 2) + 
				   name.substring(separatorIndex + 2).toLowerCase(); 
		}

		// Name is a single word.	 
		return name.substring(0,1) + name.substring(1).toLowerCase();	
	}
	 
	/**************************************************************************
	 * Create and format a horizontal box.
	 *************************************************************************/
	private Box createHorizontalBox() {		 	
		Box b = Box.createHorizontalBox();
		b.setPreferredSize(MAX_BOX_SIZE);
		b.setMaximumSize(MAX_BOX_SIZE);
		b.setMinimumSize(MIN_BOX_SIZE);
		b.setBackground(BUTTON_COLOR);
		
		return b;
	 }
	
	 /**************************************************************************
	  * Adds expandable space between rows of buttons if it is needed.
	  * @param box the horizontal box of button pairs
	  * @param boxGroup the group of button rows
	  * @param count the current number of buttons
	  *************************************************************************/
	private Vector<Box> addBoxToGroupIfFullRow(Box box, Vector<Box> boxGroup, int count) { 
		 if (count % 2 == 0) {
			 box.add(Box.createHorizontalGlue());
			 boxGroup.add(box);
		 }
		 return boxGroup;
	 }
	 
	 /**************************************************************************
	  * Adds the button to a row that holds two buttons.
	  * @param box the horizontal box of button pairs
	  * @param button the button to add
	  * @param count the current number of buttons
	  *************************************************************************/
	 private Box addNewButton(Box box, JToggleButton button, int count) {
		 // Create a new box if the current row is full.
		 if (count % 2 == 0) {
			 box = createHorizontalBox();
		 }
			
		 box.add(button);	
		 return box;
	 }
	 
	 /**************************************************************************
	  * Adds the last group of buttons, formatting the button to the left side 
	  * of the button row if the last group contains one button.
	  * @param box the horizontal box to add
	  * @param boxGroup the group of horizontal boxes
	  * @param count the current number of buttons
	  *************************************************************************/
	 private Box formatLastRow(Box box, int count) {
		 // Add an area to avoid off-centered single buttons.
		 if (count % 2 > 0) {
			 box.add(Box.createRigidArea(BUTTON_SIZE));
		 }
		 
		 return box;
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
		
		 if (selectedCommand.equals(selectAction)) {
			 parentWindow.getUMLPanel().changeInteractionMode(
					 new SelectionMode(parentWindow));
		 } 
		 else if (selectedCommand.equals(textAction)) {
			 parentWindow.getUMLPanel().changeInteractionMode(
					 new TextMode(parentWindow));
		 }
		 else {
			 for (DrawableType type: DrawableType.typeList()) {
				 if (type.name().equals(selectedCommand)) {
					 parentWindow.getUMLPanel().changeInteractionMode(
							 new DrawMode(type, parentWindow.getUMLPanel()));
					
				 }
			 }
		 }
			
	 } 
}
