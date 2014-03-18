package edu.millersville.cs.segfault.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.millersville.cs.segfault.ui.menu.UMLMenuBar;
import edu.millersville.cs.segfault.ui.panels.UMLOptionsPanel;

/**************************************************************************
 * WindowUI is the class responsible for instantiating 
 * the UML editor window.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class WindowUI extends JPanel {
	
	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = 1L;
	private JFrame winFrame;
	private WindowController winController;
	private UMLPanel umlPanel;

	//*************************************************************************
	// Constructors	
	//*************************************************************************
	
	/**************************************************************************
	 * WindowUI constructor that constructs a window
	 * and adds the basic components.
	 *************************************************************************/
	public WindowUI (WindowController wController, JFrame wFrame) {
		winFrame = wFrame;
		winController = wController;
		
		JPanel mainUIPanel = setMainPanelLayout();
		winFrame.getContentPane().add(mainUIPanel, BorderLayout.CENTER);
		
		umlPanel = new UMLPanel();

		winFrame.setJMenuBar (new UMLMenuBar(winFrame, umlPanel));
		wFrame.add(umlPanel, BorderLayout.CENTER);
		wFrame.add(new UMLOptionsPanel(umlPanel), BorderLayout.WEST);
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
	
	
	//********************************************************************
	// Mutators
	//********************************************************************
	
	/**************************************************************************
	 * Sets the layout for the main interface.
	 * @return returns the JPanel that holds all UI components.
	 *************************************************************************/
	private JPanel setMainPanelLayout() {
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(this);	
	    setLayout(new BorderLayout());
	    return mainPanel;
	}	
}
