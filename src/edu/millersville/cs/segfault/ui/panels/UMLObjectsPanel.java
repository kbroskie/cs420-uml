package edu.millersville.cs.segfault.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
//import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.ui.DrawMode;
import edu.millersville.cs.segfault.ui.UMLPanel;


/**************************************************************************
 * UMLObjectsPanel is the class responsible for
 * creating a panel containing the different
 * objects a user can select.
 * @author Kimberlyn Broskie
*************************************************************************/
public class UMLObjectsPanel extends JPanel 
							implements ActionListener{
	
	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = 6422248353297806831L;
		
	private UMLPanel umlPanel;
	
	/**** OBJECT VARIABLES */
	private static final String optionsPaneObjectDraw = "Object";

	//*************************************************************************
	// Constructors	
	//*************************************************************************
	
	/**************************************************************************
	 * Builds a panel to hold all the object options that may be drawn.
	 * @param umlPanel the panel that will hold UMLObjectsPanel. 
	 *************************************************************************/
	UMLObjectsPanel (UMLPanel umlPanel) {
		super();
		
		this.umlPanel = umlPanel;
		
		// Set the panel layout and title.
		setLayout(new GridLayout(7,1));
		setBorder(BorderFactory.createTitledBorder(
				  BorderFactory.createEmptyBorder(), "Objects", 
				  TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, 
				  new Font("SansSerif", Font.PLAIN, 15), Color.BLACK));
				
		// Create the object buttons and action listeners.
		JButton objectButton = new JButton(optionsPaneObjectDraw);
		objectButton.addActionListener(this);
		add(objectButton, BorderLayout.CENTER);
	}
	
	//*************************************************************************
	// Action Listeners
	//*************************************************************************
	
	/**************************************************************************
	* Handles the events generated by the user selecting
	* an object to draw.
	* @param se the selected event and source 
	*************************************************************************/
	public void actionPerformed(ActionEvent se) {
		String selectedCommand = se.getActionCommand();

		if (selectedCommand == optionsPaneObjectDraw ) {
			umlPanel.changeInteractionMode(new DrawMode(DrawableType.OBJECT, umlPanel));
		}			
	} 	 
}
