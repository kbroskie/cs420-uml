package edu.millersville.cs.segfault.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import edu.millersville.cs.segfault.ui.menu.UMLMenuBar;

/**************************************************************************
 * UMLWindow is the class responsible for creating
 * a UMLWindow.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class UMLWindow extends JFrame {
	
	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = 1L;
	
	
	//*************************************************************************
	// Instance Variables
	//*************************************************************************	
	// Components of the main frame.
	private UMLOptionsPanel optionsPane;
	private UMLPanel umlPanel;
	private JScrollPane scrollableUMLPanel;
	
	
	//*************************************************************************
	// Constructors	
	//*************************************************************************

	/**************************************************************************
	 * Constructor to create a window comprised of a panel that
	 * will hold the user options and a scrollable model drawing area.
	 *************************************************************************/
	public UMLWindow () {

		super("SegUE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
	
		// Panels to add to the frame.
		optionsPane = new UMLOptionsPanel(this);
		umlPanel = new UMLPanel();	
		
		// Set the options for the scroll panel.
		scrollableUMLPanel = new JScrollPane(umlPanel);
		scrollableUMLPanel.setViewportView(umlPanel);
		scrollableUMLPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollableUMLPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					
	    // Add the panels and menu to the frame.
		add(optionsPane, BorderLayout.WEST);
		add(scrollableUMLPanel, BorderLayout.CENTER);
		setJMenuBar(new UMLMenuBar(this));

		pack();
		setVisible(true);
	}
	
	
	//********************************************************************
	// Observers
	//********************************************************************
	
	/**************************************************************************
	 * Returns the current UML model.
	 * @return the current UML model
	 *************************************************************************/
	public UMLPanel getUMLPanel () {
		return umlPanel;
	}
}
