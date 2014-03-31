package edu.millersville.cs.segfault.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

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
	
	// Dimensions for the main frame.
	private static final Dimension WINDOW_PREFERRED_SIZE = new Dimension(600, 520);
	private static final Dimension UML_PANE_MIN_SIZE = new Dimension(500, 520);
	private static final Dimension OPTIONS_PANE_MIN_SIZE = new Dimension(100, 520);
	
	// Component of the main frame.
	private static UMLPanel umlPanel;

	
	//*************************************************************************
	// Instance Variables
	//*************************************************************************	
	// Components of the main frame.
	private UMLOptionsPanel optionsPane;

	private JScrollPane scrollableUMLPanel;
	private JScrollPane scrollableOptionsPanel;

	private JSplitPane splitPane;
	
	//*************************************************************************
	// Constructors	
	//*************************************************************************

	
	/**************************************************************************
	 * Constructor to create a splitpane window comprised of two scrollable 
	 * panels that hold the user options and model drawing area.
	 *************************************************************************/
	public UMLWindow () {

		super("SegUE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
	
		// Panels to add to the frame.
		umlPanel = new UMLPanel();	
		optionsPane = new UMLOptionsPanel();
		scrollableUMLPanel = new JScrollPane(umlPanel);
		scrollableOptionsPanel = new JScrollPane(optionsPane);
		scrollableOptionsPanel.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Create a split pane consisting of the drawing area and options area.
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
								   scrollableOptionsPanel, scrollableUMLPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(109);	


		// Set the components sizes.
		splitPane.setPreferredSize(WINDOW_PREFERRED_SIZE);
			optionsPane.setMinimumSize(OPTIONS_PANE_MIN_SIZE);
		scrollableUMLPanel.setMinimumSize(UML_PANE_MIN_SIZE);
		
	    // Add the splitpane and menu to the frame.
		add(splitPane);
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
	public static UMLPanel getUMLPanel () {
		return umlPanel;
	}
}
