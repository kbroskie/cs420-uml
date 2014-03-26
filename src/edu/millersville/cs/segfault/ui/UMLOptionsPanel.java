package edu.millersville.cs.segfault.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
	
	// OBJECT VARIABLES
	private static final String optionsPaneObjectDraw = "Object";
	
	// RELATION VARIABLES
	private static final String optionsPaneRelationDraw = "Relation";
	
	// OTHER OPTION VARIABLES
	private static final String optionsPaneSelect = "Select";
	
	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	// Panel containing the model
	private UMLPanel umlPanel;
		
	//*************************************************************************
	// Constructors	
	//*************************************************************************
	
	/**************************************************************************
	 * Builds a panel to hold the options for various objects and 
	 * relations that a user can select.
	 * @param umlPanel the panel for the current UML model.
	 *************************************************************************/
	 public UMLOptionsPanel (UMLPanel umlPanel) {
		 super();
		 
		 this.umlPanel = umlPanel;
		 
		 // Set the layout.
		 setLayout(new GridLayout(15, 2));
		 setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
		 
		 // Build and add the select button.
		 JButton selectionButton = new JButton(optionsPaneSelect);
		 selectionButton.addActionListener(this);
		 add(selectionButton);  
		 
		 // Create the object button and add an action listener.
		 JButton objectButton = new JButton(optionsPaneObjectDraw);
		 objectButton.addActionListener(this);
		 add(objectButton, BorderLayout.CENTER);
			   
		 // Create the relation buttons and action listeners.
		 JButton relationButton = new JButton(optionsPaneRelationDraw);
		 relationButton.addActionListener(this);
		 add(relationButton);
	} 
	 
	 
	//*************************************************************************
	// Action Listeners
	//*************************************************************************
	 
	/**************************************************************************
	 * Handles the events generated by the user selecting
	 * an option.
	 * @param se the selected event and source 
	 *************************************************************************/
	public void actionPerformed(ActionEvent se) {
		String selectedCommand = se.getActionCommand();

		if (selectedCommand == optionsPaneSelect ) {
			umlPanel.changeInteractionMode(new SelectionMode(umlPanel));
		}
		else if (selectedCommand == optionsPaneRelationDraw ) {
			umlPanel.changeInteractionMode(new DrawMode(DrawableType.RELATION, umlPanel));
		}
		else if (selectedCommand == optionsPaneObjectDraw ) {
			umlPanel.changeInteractionMode(new DrawMode(DrawableType.OBJECT, umlPanel));
		}			
	} 	 
}
