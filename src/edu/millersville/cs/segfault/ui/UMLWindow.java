package edu.millersville.cs.segfault.ui;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

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
	JPanel rightPanel;
	private Toolbar toolbar;
	//private UMLPanel umlPanel;
	//private JScrollPane scrollableUMLPanel;
	private JTabbedPane tabbedPanel;
	private HashMap<JScrollPane, UMLPanel> panels;
	private final int MAX_TAB_COUNT = 9;

	
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
	
		// Components to add to the frame.
		panels = new HashMap<JScrollPane, UMLPanel>();
		toolbar = new Toolbar(this);
		optionsPane = new OptionsPanel(this);
		
		// Create and set the tabbed panel for the model drawing area.
		tabbedPanel = new JTabbedPane();	
		tabbedPanel.setForeground(toolbar.getBackground());
		tabbedPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		createNewTab();
	
		// Create a pane with a toolbar and the drawing pane.
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(toolbar, BorderLayout.PAGE_START);	
		rightPanel.add(tabbedPanel, BorderLayout.CENTER);
		rightPanel.setBorder(BorderFactory.createMatteBorder(
				0, 1, 1, 1, getBackground().darker()));

	    // Add the panels and menu to the frame.
		add(optionsPane, BorderLayout.LINE_START);
		setJMenuBar(new MenuBar(this));
		add(rightPanel);

		pack();
		setVisible(true);
	}
	
	
	//********************************************************************
	// Mutators
	//********************************************************************
	
	/**************************************************************************
	 * Returns a scrollable UMLPanel
	 * @return a scrollable UMLPanel
	 *************************************************************************/
	public JScrollPane createScrollableUMLPanel(UMLPanel panel) {		
		JScrollPane scrollPanel = new JScrollPane(panel);
		scrollPanel.setViewportView(panel);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPanel.setAutoscrolls(true);
		
		return scrollPanel;
	}
	
	/**************************************************************************
	 * Adds a new tab to the tabbed panel.
	 *************************************************************************/
	public void createNewTab() {
		int newTabNumber = tabbedPanel.getTabCount() + 1;

		if (newTabNumber > MAX_TAB_COUNT) {
			String message = "The maximum number of tabs is " + MAX_TAB_COUNT + ".\n" + 
					  "Please close one or more tabs before adding a tab.";
			JOptionPane.showMessageDialog(null, message, "Maximum Amount of Tabs are Open", 
										  JOptionPane.ERROR_MESSAGE);
		}
		else {
			UMLPanel uml = new UMLPanel();
			JScrollPane scrollpanel = createScrollableUMLPanel(uml);
			tabbedPanel.insertTab("New Tab " +  newTabNumber, null, scrollpanel, null, newTabNumber - 1);
			tabbedPanel.setSelectedIndex(newTabNumber - 1);
			panels.put(scrollpanel, uml);
		}
	}
	
	public void loadNewTab() {
		int newTabNumber = tabbedPanel.getTabCount() + 1;
		
		if (newTabNumber > MAX_TAB_COUNT) {
			String message = "The maximum number of tabs is " + MAX_TAB_COUNT + ".\n" + 
					  "Please close one or more tabs before loading a file.";
			JOptionPane.showMessageDialog(null, message, "Cannot Load File", 
										  JOptionPane.ERROR_MESSAGE);
		}
		else {
			UMLPanel uml = new UMLPanel(); 
			uml.load();

			JScrollPane scrollpanel = createScrollableUMLPanel(uml);
			tabbedPanel.insertTab("New Tab " +  newTabNumber, null, scrollpanel, null, newTabNumber - 1);
			tabbedPanel.setSelectedIndex(newTabNumber - 1);
			panels.put(scrollpanel, uml);
		}
	}
	
	//********************************************************************
	// Observers
	//********************************************************************
	
	/**************************************************************************
	 * Returns the current UML model.
	 * @return the current UML model
	 *************************************************************************/
	public UMLPanel getUMLPanel() {
		return panels.get(tabbedPanel.getSelectedComponent());
	}
	
	/**************************************************************************
	 * Returns the toolbar.
	 * @return the toolbar.
	 *************************************************************************/
	public Toolbar getToolbar() {
		return toolbar;
	}
}
