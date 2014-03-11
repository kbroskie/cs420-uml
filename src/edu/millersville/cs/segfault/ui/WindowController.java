package edu.millersville.cs.segfault.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Window is the class responsible for instantiating 
 * and controlling the UML editor window.
 * @author Kimberlyn Broskie
 *
 */
public class WindowController {
	private JFrame windowFrame;
	private WindowUI winUI;
	private UMLPanel umlPanel;
	
	/**
	 * Constructs the UML window object.
	 */
	private WindowController() {
	    // Set the look and feel.
	    if (System.getProperty("mrj.version") != null) {
	    	System.setProperty("apple.laf.useScreenMenuBar", "true");
	    }
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    
	    windowFrame = new JFrame("SegUE");    
	    windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    windowFrame.setLayout(new BorderLayout());
	    umlPanel = new UMLPanel();
	    winUI = new WindowUI(this, windowFrame);
	    windowFrame.pack();
	    windowFrame.setVisible(true);
	}
	
	public UMLPanel uml() {
		return umlPanel;
	}
	
	public WindowUI windowUI() {
		return winUI;
	}

	/*
	 * Creates and shows the UML editor GUI.
	 * @param args are not used
	 */
	public static void main(String[] args) {
	   javax.swing.SwingUtilities.invokeLater(new Runnable() {
		   public void run() {
			   new WindowController();
		   }
	   });
	}
}
