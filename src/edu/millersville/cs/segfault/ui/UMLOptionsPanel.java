package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.millersville.cs.segfault.model.DrawableType;

/**************************************************************************
 * UMLOptionsPanel is the class responsible for
 * creating a panel containing the different
 * objects and relations a user can select.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class UMLOptionsPanel extends JPanel
							implements ActionListener {

	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = -9118912567292664779L;

	
	// Non-Drawable Action Commands
	private static final String selectAction = "SELECT";
	private static final String textAction   = "TEXT";
	
	//*************************************************************************
	// Constructors	
	//*************************************************************************
	
	/**************************************************************************
	 * Builds a panel to hold the options for various objects and 
	 * relations that a user can select.
	 * @param umlPanel the panel for the current UML model.
	 *************************************************************************/
	 public UMLOptionsPanel () {
		 super();
		 		 
		 // Set the layout.
		 setLayout(new GridLayout(0,2));
		 setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
		 
		 // Build and add the select button.
		 JButton selectionButton = new JButton(new ImageIcon("img/64/selectionMode.png"));
		 selectionButton.setActionCommand(selectAction);
		 selectionButton.setPreferredSize(new Dimension(64, 64));
		 selectionButton.addActionListener(this);
		 add(selectionButton);  
		 
		 // Build and add the text button
		 JButton textButton = new JButton(new ImageIcon("img/64/textMode.png"));
		 textButton.setActionCommand(textAction);
		 textButton.setPreferredSize(new Dimension(64, 64));
		 add(textButton);
		 
		 // Add all the objects
		 
		 for (DrawableType type : DrawableType.objectTypeList()) {
			 JButton newButton = new JButton(type.icon);
			 newButton.setActionCommand(type.name());
			 newButton.setPreferredSize(new Dimension(64, 64));
			 newButton.addActionListener(this);
			 
			 add(newButton);
		 }
		 
		 for (DrawableType type: DrawableType.relationTypeList()) {
			 if (type == DrawableType.RELATION) { continue; }
			 JButton newButton = new JButton(type.icon);
			 newButton.setActionCommand(type.name());
			 newButton.setPreferredSize(new Dimension(64, 64));
			 newButton.addActionListener(this);
			 add(newButton);
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
		System.out.println(selectedCommand);
		
		if (selectedCommand.equals(selectAction)) {
			UMLWindow.getUMLPanel().changeInteractionMode(
					new SelectionMode(UMLWindow.getUMLPanel()));
		} else {
			for (DrawableType type: DrawableType.typeList()) {
				if (type.name().equals(selectedCommand)) {
					UMLWindow.getUMLPanel().changeInteractionMode(new DrawMode(type, UMLWindow.getUMLPanel()));
				}
			}
		}
			
	} 	 
}
