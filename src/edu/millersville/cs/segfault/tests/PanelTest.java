package edu.millersville.cs.segfault.tests;

import javax.swing.JFrame;

import edu.millersville.cs.segfault.ui.UMLPanel;

public class PanelTest {
	public static void main(String[] args) {
		JFrame testFrame = new JFrame("UML Panel Test");
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.add(new UMLPanel());
		testFrame.pack();
		testFrame.setVisible(true);
	}
}
