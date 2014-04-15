package edu.millersville.cs.segfault.ui;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
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
	private TabbedUMLPanel tabbedUMLPanel;
	JPanel rightPanel;
	private Toolbar toolbar;
	private UMLPanel umlPanel;
	private final int Virtual_Key_0 = 48;
	private JScrollPane scrollableUMLPanel;
	private JTabbedPane tabbedPanel;
	//private UMLPanel panel1;
	private HashMap<JScrollPane, UMLPanel> panels;

	
	//*************************************************************************
	// Constructors	
	//*************************************************************************

	/**************************************************************************
	 * Constructor to create a window comprised of a panel that
	 * will hold the user options and a scrollable model drawing area.
	 *************************************************************************/
	public UMLWindow () {
		


		super("SegUE");

		panels = new HashMap<JScrollPane, UMLPanel>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
	
		// Panels to add to the frame.
		toolbar = new Toolbar(this);
		optionsPane = new OptionsPanel(this);
		//scrollableUMLPanel = new JScrollPane(umlPanel);
		//scrollableUMLPanel.setViewportView(umlPanel);
		//scrollableUMLPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollableUMLPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		//tabbedUMLPanel = new TabbedUMLPanel();
		//tabbedUMLPanel.setForeground(toolbar.getBackground());
		
		JPanel pane = new JPanel();
		
		//panel1 = new UMLPanel();
		
		umlPanel = new UMLPanel();
		scrollableUMLPanel = new JScrollPane(umlPanel);
		panels.put(scrollableUMLPanel, umlPanel);
		scrollableUMLPanel.setViewportView(umlPanel);
		scrollableUMLPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollableUMLPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		tabbedPanel = new JTabbedPane();	
		tabbedPanel.setForeground(toolbar.getBackground());
		tabbedPanel.add("Tab 1", scrollableUMLPanel);
		//tabbedPanel.
		
		
		//Component newTab = tabbedPanel.getComponent(0);
		//scrollableUMLPanel.setViewportView(newTab);

		
		
		
		//JScrollPane scrollpane = new JScrollPane(newTab);
		
		//scrollpane.setViewportView(newTab);
		//scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		//tabbedPanel.add(scrollpane);
		
		tabbedPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			
		// Create a pane with a toolbar and the drawing pane.
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(toolbar, BorderLayout.PAGE_START);
		//rightPanel.add(scrollableUMLPanel, BorderLayout.CENTER);
		//rightPanel.add(new TabbedUMLPanel(), BorderLayout.CENTER);
		//rightPanel.add(scrollableUMLPanel, BorderLayout.CENTER);

		rightPanel.add(tabbedPanel, BorderLayout.CENTER);
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
