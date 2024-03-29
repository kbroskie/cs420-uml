package edu.millersville.cs.segfault.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;


/**************************************************************************
 * ButtonTab is the class responsible for creating
 * a closeable tab button.
 * @author Kimberlyn Broskie
 *************************************************************************/
public class ButtonTab extends JPanel {
	
	//*************************************************************************
	// Static Instance Variables

	private static final long serialVersionUID = -8719959125259611923L;
	
	//*************************************************************************
	// Instance Variables

	private JTabbedPane currentPane;

	/**************************************************************************
	* Constructor that builds a closeable button.
	* @param pane the tabbedpane the button will be placed on
	* @param tabTitle the title to display on the tab
	*************************************************************************/
	ButtonTab(final JTabbedPane pane, String tabTitle) {
		super(new BorderLayout());
		
	    // Check that the tabbedpane exists.
		if (pane == null) {
	    	throw new NullPointerException("TabbedPane is null");
	    }
	    this.currentPane = pane;
	    
	    // Create and format a label for the title a button to be placed on.
		JLabel titleLabel = new JLabel(tabTitle);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 7));
	    add(titleLabel, BorderLayout.WEST);
	    
	    // Create and format the button to place on the label.
	    JButton button = new TabPaneButton();
	    add(button, BorderLayout.EAST);
	    setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
	    setOpaque(false);
	}
	
	//********************************************************************
	// Producers
		
	// Inner class to create and format the button.
	private class TabPaneButton extends JButton {
		private static final long serialVersionUID = 7476481130690325277L;

		public TabPaneButton() {
			super();
	       	setPreferredSize(new Dimension(16, 16));
	       	setToolTipText("Close Tab");
	       	setUI(new BasicButtonUI());
	       	setIcon(new ImageIcon("img/16/Destroy.png"));
	       	setContentAreaFilled(false);
	   		setBorderPainted(false);
	   		setFocusPainted(true);
	   		setRolloverEnabled(true);
	       	addActionListener(new TabButtonListener());
	    }
	}
	
	//*************************************************************************
	// Action Listeners
		
	// Private inner class that handles the events generated by the user 
	// clicking a TabButton
	private class TabButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int i = currentPane.indexOfTabComponent(ButtonTab.this);
			if (i != -1) {
				int closeTabOption = JOptionPane.YES_NO_OPTION;
			    int response = JOptionPane.showConfirmDialog (null, "Are you sure you want to close this tab?", 
			    		"Unsaved work will be lost!", JOptionPane.YES_NO_OPTION);
			            
			    if(closeTabOption == response) {
			    	currentPane.remove(i);
			    }
			}
		}
	}
}