package edu.millersville.cs.segfault.ui.menu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

import edu.millersville.cs.segfault.ui.UMLWindow;
import edu.millersville.cs.segfault.ui.console.Console;

/*****************************************************************************
 * A menu for enabling and disabling subviews in the main window.
 * 
 * @author Daniel Rabiega
 */

public class ViewMenu extends JMenu implements ActionListener {

	private boolean consoleOn = false;
	private UMLWindow parent;
	
	private JCheckBoxMenuItem consoleItem;
	private Console console=null;
	
	/*************************************************************************
	 * Builds a viewMenu and initializes it with a reference to it's parent.
	 */
	public ViewMenu(UMLWindow parent) {
		super("View");
		this.parent = parent;
		
		this.consoleItem = new JCheckBoxMenuItem("Console");
		consoleItem.addActionListener(this);
		consoleItem.setActionCommand("CONSOLE");
		this.add(consoleItem);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("CONSOLE")) {
			if (consoleOn) {
				parent.remove(consoleItem);
			} else {
				if (console == null) {
					this.console = new Console();
				}
				parent.add(console, BorderLayout.SOUTH);
				parent.pack();
			}
			consoleOn = !consoleOn;
		}
		
	}

}
