package edu.millersville.cs.segfault.ui.menu.actions;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.net.URI;

import javax.swing.AbstractAction;

import edu.millersville.cs.segfault.ui.menu.ActionType;
import edu.millersville.cs.segfault.ui.menu.MenuAction;


/**************************************************************************
 * Help is the class responsible for displaying the help file.
 * @author Daniel Rabiega, Kimberlyn Broskie
 *************************************************************************/
public class Help extends AbstractAction 
						implements MenuAction{
	
	//*************************************************************************
	// Static Instance Variables
	//*************************************************************************
	private static final long serialVersionUID = -8781235292253313426L;
	private static final String helpMenuText = "Help";

	/**************************************************************************
	* Constructor that builds the action with an accelerator.
	*************************************************************************/
	public Help ()
	{
		super(helpMenuText);
	}
	
	//*************************************************************************
	// Observers
	//*************************************************************************
	@Override
	public ActionType getType() { return ActionType.HELP; }
	
	//*************************************************************************
	// Event Listeners
	//*************************************************************************
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				if (desktop.isSupported(Desktop.Action.BROWSE)) {
					desktop.browse(new URI("http://verdantneuron.com/Manual.html"));
				}
			}
		} catch (Exception ex) {
			
		}
	}
}
