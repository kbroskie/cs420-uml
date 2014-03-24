package edu.millersville.cs.segfault.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

import edu.millersville.cs.segfault.ui.menu.UMLMenuBar;

/**************************************************************************
 * WindowUI is the class responsible for instantiating 
 * the UML editor window.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class WindowUI extends JPanel {
	
	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = 1L;
	
	// Dimensions for the main frame.
	private static final Dimension WINDOW_PREFERRED_SIZE = new Dimension(600, 520);
	private static final Dimension UML_PANE_MIN_SIZE = new Dimension(500, 520);
	private static final Dimension OPTIONS_PANE_MIN_SIZE = new Dimension(100, 520);
	
	
	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	private JFrame winFrame;
	private WindowController winController;
	
	// Components of the main frame.
	private UMLPanel umlPanel;
	private JScrollPane scrollableUMLPanel;
	private JSplitPane splitPane;
	private UMLOptionsPanel optionsPane;
	private JScrollPane scrollableOptionsPanel;
	
	
	//*************************************************************************
	// Constructors	
	//*************************************************************************
	
	/**************************************************************************
	 * Constructor to create a splitpane window with two main panels.
	 *************************************************************************/
	public WindowUI (WindowController wController, JFrame wFrame) {
		winFrame = wFrame;
		winController = wController;
		
		// Panels to add to the main frame.
		umlPanel = new UMLPanel();	
		optionsPane = new UMLOptionsPanel(umlPanel);
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
		
	    // Add the splitpane and menu to the main frame.
		winFrame.getContentPane().add(splitPane);
	    winFrame.setJMenuBar(new UMLMenuBar(winFrame, umlPanel));
	}
	
	
	//********************************************************************
	// Observers
	//********************************************************************
	
	/**************************************************************************
	 * Returns the current UML model.
	 * @return the current UML model
	 *************************************************************************/
	public UMLPanel uml () {
		return winController.uml();
	}
}
