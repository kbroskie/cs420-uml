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
 * UMLOptionsPanel is the class responsible for
 * creating a panel containing the different
 * objects and relations a user can select.
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
	//private static final String textAction   = "TEXT";
	
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
	 * @param umlPanel the panel for the current UML model.
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
		 
		 ButtonGroup optionsButtonGroup = new ButtonGroup();
		 
		 // To store the created buttons.
		 Vector<Box> buttonBoxes = new Vector<Box>(10);

		 // Build and add the select button.
		 JToggleButton selectionButton = 
				 new JToggleButton(new ImageIcon("img/64/selectionMode.png"));
		 selectionButton.setSelectedIcon(new ImageIcon("img/64/down/selectionMode.png"));
		 selectionButton.setActionCommand(selectAction);
		 selectionButton.setPreferredSize(new Dimension(64, 64));
		 selectionButton.addActionListener(this);
		 selectionButton.setToolTipText(selectAction.substring(0,1) + 
				 selectAction.substring(1).toLowerCase());
		 selectionButton.setMaximumSize(BUTTON_SIZE);
		 selectionButton.setPreferredSize(BUTTON_SIZE);
		 selectionButton.setMinimumSize(BUTTON_SIZE);
		 selectionButton.setSelected(true);
		 selectionButton.setBorderPainted(false);
		 selectionButton.setFocusPainted(false);
		 selectionButton.setBackground(BUTTON_COLOR);
		 optionsButtonGroup.add(selectionButton);

//		 // Build and add the text button
//		 JButton textButton = new JButton(new ImageIcon("img/64/textMode.png"));
//		 textButton.setActionCommand(textAction);
//		 textButton.setPreferredSize(new Dimension(64, 64));
//		 add(textButton);
		
		 // Create a box to hold groups of two buttons.
		 Box newBox = Box.createHorizontalBox();
		 newBox.setPreferredSize(MAX_BOX_SIZE);
		 newBox.setMaximumSize(MAX_BOX_SIZE);
		 newBox.setMinimumSize(MIN_BOX_SIZE);
		 newBox.setBackground(BUTTON_COLOR);	
		 
		 newBox.add(selectionButton);
			 
		 // To keep track of when to add a new box.
		 int buttonCount = 1;
		
		 // Add the object buttons and tooltips to the button group and toolbarBox.
		 for (DrawableType type : DrawableType.objectTypeList()) {
			 JToggleButton newButton = new JToggleButton(type.icon);
			 newButton.setSelectedIcon(type.selectedIcon);
			 newButton.setActionCommand(type.name());
			 newButton.setMaximumSize(BUTTON_SIZE);
			 newButton.setPreferredSize(BUTTON_SIZE);
			 newButton.setToolTipText(type.name());
			 newButton.addActionListener(this);
			 newButton.setBackground(BUTTON_COLOR);
			 newButton.setBorderPainted(false);
			 newButton.setFocusPainted(false);
			 	
			 // Replace underscores with spaces, and leave the first letter 
			 // of each word capitalized.
			 int separatorIndex = type.name().indexOf("_");

			 if (separatorIndex > 0) {
				 newButton.setToolTipText(type.name().substring(0,1) + 
						 type.name().substring(1, separatorIndex + 1).toLowerCase().replace("_", " ") +
						 type.name().substring(separatorIndex + 1, separatorIndex + 2) + 
						 type.name().substring(separatorIndex + 2).toLowerCase()); 
			 }
			 else {
				 newButton.setToolTipText(type.name().substring(0,1) + 
						 type.name().substring(1).toLowerCase());
			 }
			
			 // Add the current box and create a new one before
			 // adding the new button.
			 if (buttonCount % 2 == 0) {
				 newBox.add(Box.createHorizontalGlue());
				 buttonBoxes.add(newBox);
				 newBox = Box.createHorizontalBox();
				 newBox.setPreferredSize(MAX_BOX_SIZE);
				 newBox.setMaximumSize(MAX_BOX_SIZE);
				 newBox.setMinimumSize(MIN_BOX_SIZE);
				 newBox.setBackground(BUTTON_COLOR);
			 }
			
			 newBox.add(newButton);	
			 optionsButtonGroup.add(newButton);	
			 ++buttonCount;	
		 }
		
		 // Add the relation buttons and tooltips to the button group and toolbarBox.
		 for (DrawableType type: DrawableType.relationTypeList()) {
			 if (type == DrawableType.RELATION) { continue; }
			 JToggleButton newButton = new JToggleButton(type.icon);
			 newButton.setSelectedIcon(type.selectedIcon);
			 newButton.setActionCommand(type.name());
			 newButton.setMaximumSize(BUTTON_SIZE);
			 newButton.setPreferredSize(BUTTON_SIZE);
			 newButton.addActionListener(this);
			 newButton.setBorderPainted(false);
			 newButton.setFocusPainted(false);
			 newButton.setBackground(BUTTON_COLOR);
			 newButton.setToolTipText(type.name().substring(0,1) + 
					 type.name().substring(1).toLowerCase());

			 // Add the current box and create a new one before
			 // adding the new button.
			 if (buttonCount % 2 == 0) {
				 newBox.add(Box.createHorizontalGlue());
				 buttonBoxes.add(newBox);
				 newBox = Box.createHorizontalBox();
				 newBox.setPreferredSize(MAX_BOX_SIZE);
				 newBox.setMaximumSize(MAX_BOX_SIZE);
				 newBox.setMinimumSize(MIN_BOX_SIZE);
				 newBox.setBackground(BUTTON_COLOR);
			 }
			
			 newBox.add(newButton);
			 optionsButtonGroup.add(newButton); 
			 ++buttonCount;
		 }	 
		
		 // Add an area to avoid off-centered single buttons.
		 if (buttonCount % 2 > 0) {
			 newBox.add(Box.createRigidArea(BUTTON_SIZE));
		 }

		 buttonBoxes.add(newBox);

		 for (Object b : buttonBoxes) {
			 add((Component) b);
		 }
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
					 new SelectionMode(parentWindow.getUMLPanel()));
		 } else {
			 for (DrawableType type: DrawableType.typeList()) {
				 if (type.name().equals(selectedCommand)) {
					 parentWindow.getUMLPanel().changeInteractionMode(
							 new DrawMode(type, parentWindow.getUMLPanel()));
					
				 }
			 }
		 }
			
	 } 
}
