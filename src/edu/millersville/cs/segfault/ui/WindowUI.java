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

<<<<<<< HEAD
	   // Add the sub-menus to the File menu.
	   fileMenu.add(newItem);
	   fileMenu.add(openItem);
	   fileMenu.add(saveItem);
	   fileMenu.add(saveAsItem);
	   fileMenu.add(exitItem);
	   
	   return fileMenu;
	}

	/*
	 * Builds the edit sub-menu.
	 * @return returns the edit submenu
	 */
	private JMenu buildEditMenu() {
	   // Create the edit submenu items, and add accelerators.
	   JMenu editMenu = new JMenu(editMenuText);
	   
	   JMenuItem undoItem = new JMenuItem(undoMenuText);
	   undoItem.addActionListener(this);
	   undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));

	   JMenuItem redoItem = new JMenuItem(redoMenuText);
	   redoItem.addActionListener(this);
	   redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK));
	   
	   JMenuItem selectAllItem = new JMenuItem(selectAllText);
	   selectAllItem.addActionListener(this);
	   selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
	   
	   JMenuItem deleteItem = new JMenuItem(deleteText);
	   deleteItem.addActionListener(this);
	   deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
	   
	   // Add the sub-menus to the Edit menu.
	   editMenu.add(undoItem);
	   editMenu.add(redoItem);
	   editMenu.add(selectAllItem);
	   editMenu.add(deleteItem);
	   
	   return editMenu;
	}
	
	/**
	 * Builds the UML panel.
	 * @param wFrame the frame for the interface
	 */
	private void buildUMLPanel(JFrame wFrame) {
		umlPanel = new UMLPanel();
		umlPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		wFrame.add(umlPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Creates a panel to place the various UML object selections.
	 * @param wFrame the frame for the interface
	 */
	private void buildOptionsPanel(JFrame wFrame) {
		
	   // Create the main panel and set the layout.
	   GridBagLayout gridBag = new GridBagLayout();
	   JPanel optionsPanel = new JPanel();
	   optionsPanel.setLayout(gridBag);
	   optionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
	   
	   // Set the gridbag options for the options panel.
	   GridBagConstraints optionsConstraints = new GridBagConstraints();
	   optionsConstraints.fill = GridBagConstraints.HORIZONTAL;
	   optionsConstraints.anchor = GridBagConstraints.PAGE_START;
	   optionsConstraints.gridx = 0;
	   optionsConstraints.gridy = 0;
	   optionsConstraints.weighty = 0.5;
	   optionsConstraints.insets = new Insets(2,0,0,0);
	   	   
	   // Build and add the objects panel
	   JPanel objectsPanel = new JPanel();
	   buildObjectsPanel(objectsPanel);
	   gridBag.setConstraints(objectsPanel, optionsConstraints);
	   optionsPanel.add(objectsPanel);   

	   // Build and add the relations panel.
	   ++optionsConstraints.gridy;
	   JPanel relationsPanel = new JPanel();
	   buildRelationsPanel(relationsPanel);  
	   gridBag.setConstraints(relationsPanel, optionsConstraints);	  
	   optionsPanel.add(relationsPanel);
	   
	   // Set the options so the last item appears on the bottom of the panel.
	   ++optionsConstraints.gridy;
	   optionsConstraints.weighty = 1.0;
	   optionsConstraints.anchor = GridBagConstraints.PAGE_END;
	   optionsConstraints.insets = new Insets(2,5,3,5);

	   // Build and add the select button.
	   JButton selectionButton = new JButton(optionsPaneSelect);
	   selectionButton.addActionListener(this);
	   gridBag.setConstraints(selectionButton, optionsConstraints);
	   optionsPanel.add(selectionButton);
	   
	   // Add the optionsPanel to the main frame.
	   wFrame.add(optionsPanel, BorderLayout.WEST);
	} 
	
	/**
	 * Builds a panel to hold all the object options for the main options panel.
	 * @param objectsPanel Holds all the object options a user can select.
	 */
	private void buildObjectsPanel(JPanel objectsPanel) {
		// Set the panel layout and title.
		objectsPanel.setLayout(new GridLayout(7,1));
		objectsPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(), "Objects", 
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, 
				new Font("SansSerif", Font.PLAIN, 15), Color.BLACK));
		
		// Create the object buttons and action listeners.
		JButton objectButton = new JButton(optionsPaneObjectDraw);
		objectButton.addActionListener(this);
		objectsPanel.add(objectButton, BorderLayout.CENTER);		
		
		JButton classButton = new JButton(optionsPaneClassDraw);
		classButton.addActionListener(this);
		objectsPanel.add(classButton, BorderLayout.CENTER);
		
		JButton activeButton = new JButton(optionsPaneActiveClassDraw);
		activeButton.addActionListener(this);
		objectsPanel.add(activeButton, BorderLayout.CENTER);
		
		JButton componentButton = new JButton(optionsPaneComponentDraw);
		componentButton.addActionListener(this);
		objectsPanel.add(componentButton, BorderLayout.CENTER);
	}
	
	/**
	 * Builds a panel to hold all the relations options for the main options panel.
	 * @param relationsPanel Holds all the relation options a user can select.
	 */
	private void buildRelationsPanel(JPanel relationsPanel) {
		// Set the panel layout and title.
		relationsPanel.setLayout(new GridLayout(7,1));
		relationsPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(), "Relations", 
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, 
				new Font("SansSerif", Font.PLAIN, 15), Color.BLACK));
		
		// Create the object buttons and action listeners.
		JButton relationButton = new JButton(optionsPaneRelationDraw);
		relationButton.addActionListener(this);
		relationsPanel.add(relationButton);
		
		JButton aggregationButton = new JButton(optionsPaneAggregationDraw);
		aggregationButton.addActionListener(this);
		relationsPanel.add(aggregationButton);
		
		JButton compositionButton = new JButton(optionsPaneCompositionDraw);
		compositionButton.addActionListener(this);
		relationsPanel.add(compositionButton);
		
		JButton associationButton = new JButton(optionsPaneAssociationDraw);
		associationButton.addActionListener(this);
		relationsPanel.add(associationButton);
		
	}	
=======
		// Set the components sizes.
		splitPane.setPreferredSize(WINDOW_PREFERRED_SIZE);
		optionsPane.setMinimumSize(OPTIONS_PANE_MIN_SIZE);
		scrollableUMLPanel.setMinimumSize(UML_PANE_MIN_SIZE);
		
	    // Add the splitpane and menu to the main frame.
		winFrame.getContentPane().add(splitPane);
	    winFrame.setJMenuBar(new UMLMenuBar(winFrame, umlPanel));
	}
	
>>>>>>> gui_improvement
	
	//********************************************************************
	// Observers
	//********************************************************************
	
<<<<<<< HEAD
	/**
	 * Handles the events generated by the user selecting
	 * a menu option.
	 * @param se the selected event and source 
	 */
	 public void actionPerformed(ActionEvent se) {
		 String selectedCommand = se.getActionCommand();
		 	 	 
		 if (selectedCommand == newMenuText) {
			 umlPanel.changeModel(new UMLModel());
		 }
		 else if (selectedCommand == openMenuText) {
			 umlPanel.load();
		 }
		 else if (selectedCommand == saveMenuText) {
			 umlPanel.save(umlPanel.getModel().serialize());
		 }
		 else if (selectedCommand == saveAsMenuText) {
			 umlPanel.saveAs(umlPanel.getModel().serialize());
		 }
		 else if (selectedCommand == undoMenuText) {
			 umlPanel.undo();	
		 }
		 else if (selectedCommand == redoMenuText) {
			 umlPanel.redo();
		 }
		 else if (selectedCommand == selectAllText) {
			 umlPanel.select();
		 }
		 else if (selectedCommand == deleteText ) {
			 umlPanel.changeModel(umlPanel.getModel().deleteSelected());
		 } else if (selectedCommand == optionsPaneObjectDraw ) {
			 umlPanel.changeInteractionMode(new DrawMode(DrawableType.OBJECT, umlPanel));
		 } else if (selectedCommand == optionsPaneClassDraw){
			 umlPanel.changeInteractionMode(new DrawMode(DrawableType.CLASS, umlPanel));
		 } else if (selectedCommand == optionsPaneRelationDraw ) {
			 umlPanel.changeInteractionMode(new DrawMode(DrawableType.RELATION, umlPanel));
		 } else if (selectedCommand == optionsPaneSelect ) {
			 umlPanel.changeInteractionMode(new SelectionMode(umlPanel));
		 } else if (selectedCommand == optionsPaneAggregationDraw) {
			 umlPanel.changeInteractionMode(new DrawMode(DrawableType.AGGREGATION, umlPanel));
		 } else if (selectedCommand == optionsPaneActiveClassDraw) {
			 umlPanel.changeInteractionMode(new DrawMode(DrawableType.ACTIVE_CLASS, umlPanel));
		 } else if (selectedCommand == optionsPaneComponentDraw) {
			 umlPanel.changeInteractionMode(new DrawMode(DrawableType.COMPONENT, umlPanel));
		 } else if (selectedCommand == optionsPaneCompositionDraw) {
			 umlPanel.changeInteractionMode(new DrawMode(DrawableType.COMPOSITION, umlPanel));
		 } else if (selectedCommand == optionsPaneAssociationDraw) {
			 umlPanel.changeInteractionMode(new DrawMode(DrawableType.ASSOCIATION, umlPanel));
		 }
	 } 	 
=======
	/**************************************************************************
	 * Returns the current UML model.
	 * @return the current UML model
	 *************************************************************************/
	public UMLPanel uml () {
		return winController.uml();
	}
>>>>>>> gui_improvement
}
