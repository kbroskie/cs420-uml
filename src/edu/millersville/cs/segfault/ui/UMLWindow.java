package edu.millersville.cs.segfault.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.millersville.cs.segfault.ui.menu.MenuBar;

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
	private OptionsPanel optionsPane;
	private UMLPanel umlPanel;
	private JScrollPane scrollableUMLPanel;
	JPanel rightPanel;
	private Toolbar toolbar;
	
	
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
		setExtendedState(	 JFrame.MAXIMIZED_BOTH);
	
		// Panels to add to the frame.
		toolbar = new Toolbar(this);
		optionsPane = new OptionsPanel(this);
		umlPanel = new UMLPanel();			
		
		// Set the options for the scroll panel.
		scrollableUMLPanel = new JScrollPane(umlPanel);
		scrollableUMLPanel.setViewportView(umlPanel);
		scrollableUMLPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollableUMLPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					
		// Create a pane with a toolbar and the drawing pane.
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(toolbar, BorderLayout.PAGE_START);
		rightPanel.add(scrollableUMLPanel, BorderLayout.CENTER);
		rightPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, getBackground().darker()));

	    // Add the panels and menu to the frame.
		add(optionsPane, BorderLayout.LINE_START);
		setJMenuBar(new MenuBar(this));
		add(rightPanel);

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
	public UMLPanel getUMLPanel() {
		return umlPanel;
	}
	
	/**************************************************************************
	 * Returns the toolbar.
	 * @return the toolbar.
	 *************************************************************************/
	public Toolbar getToolbar() {
		return toolbar;
	}
}
