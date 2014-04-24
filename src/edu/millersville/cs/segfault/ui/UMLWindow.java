package edu.millersville.cs.segfault.ui;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import edu.millersville.cs.segfault.immutable.ImmutableSet;
import edu.millersville.cs.segfault.model.DrawableUML;
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
	private JPanel rightPanel;
	private Toolbar toolbar;
	private JTabbedPane tabbedPanel;
	private HashMap<JScrollPane, UMLPanel> panels;
	private ImmutableSet<DrawableUML> pasteBuffer;
	
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
		scrollPanel.setWheelScrollingEnabled(true);
		scrollPanel.setPreferredSize(panel.getPreferredSize());
		
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
			UMLPanel uml = new UMLPanel(this);
			JScrollPane scrollpanel = createScrollableUMLPanel(uml);
			String tabTitle = "New Tab " +  newTabNumber;
			
			tabbedPanel.insertTab(tabTitle, new ImageIcon("img/16/Destroy.png"), 
					scrollpanel, null, newTabNumber - 1);
			tabbedPanel.setSelectedIndex(newTabNumber - 1);
			tabbedPanel.setTabComponentAt(newTabNumber - 1, new ButtonTab(tabbedPanel, tabTitle));
			panels.put(scrollpanel, uml);
		}
	}
	
	/**************************************************************************
	 * Adds a tab to the tabbed panel that contains a saved diagram from the 
	 * location specified by the user.
	 *************************************************************************/
	public void loadNewTab() {
		int newTabIndex = tabbedPanel.getTabCount();
		
		if (newTabIndex + 1 > MAX_TAB_COUNT) {
			String message = "The maximum number of tabs is " + MAX_TAB_COUNT + ".\n" + 
					  "Please close one or more tabs before loading a file.";
			JOptionPane.showMessageDialog(null, message, "Cannot Load File", 
										  JOptionPane.ERROR_MESSAGE);
		}
		else {
			UMLPanel uml = new UMLPanel(this); 
			JScrollPane scrollpanel = createScrollableUMLPanel(uml);
			String tabTitle = "New Tab " +  (newTabIndex + 1);
			
			tabbedPanel.insertTab(tabTitle, new ImageIcon("img/16/Destroy.png"), 
 					scrollpanel, null, newTabIndex);
			tabbedPanel.setSelectedIndex(newTabIndex);
			tabbedPanel.setTabComponentAt(newTabIndex, new ButtonTab(tabbedPanel, tabTitle));
			panels.put(scrollpanel, uml);
			panels.get(tabbedPanel.getSelectedComponent()).load();
		}
	}
	
	//********************************************************************
	// Observers
	//********************************************************************
	
	/**************************************************************************
	 * Returns the current UML model.
	 * @return the current UML model for the selected tab.
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
	
	/**************************************************************************
	 * Returns the contents of the current Pastebuffer
	 * @return the pastebuffer
	 *************************************************************************/
	public ImmutableSet<DrawableUML> getPasteBuffer() {
		return pasteBuffer;
	}
	
	/**************************************************************************
	 * Adds input to the pastebuffer
	 *************************************************************************/
	public void setPasteBuffer(ImmutableSet<DrawableUML> selected) {
		pasteBuffer = selected;
	}
	
	//********************************************************************
	// Mutators
	//********************************************************************
	
	/**************************************************************************
	 * Set the tab title to the name of the current file.
	 *************************************************************************/
	public void updateTabname() {
		String title = panels.get(tabbedPanel.getSelectedComponent()).getFilename();
		title = title.substring(title.lastIndexOf("\\") + 1, title.indexOf("."));
		tabbedPanel.setTabComponentAt(tabbedPanel.getSelectedIndex(), new ButtonTab(tabbedPanel, title));
	}
}
