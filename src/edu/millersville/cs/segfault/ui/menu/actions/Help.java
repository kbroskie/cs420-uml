package edu.millersville.cs.segfault.ui.menu.actions;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.net.URI;

import javax.swing.AbstractAction;

import edu.millersville.cs.segfault.ui.menu.ActionType;
import edu.millersville.cs.segfault.ui.menu.MenuAction;

public class Help extends AbstractAction implements MenuAction {

	private static final long serialVersionUID = -8781235292253313426L;

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				if (desktop.isSupported(Desktop.Action.BROWSE)) {
					desktop.browse(new URI("docs/index.html"));
				}
			}
		} catch (Exception ex) {
			
		}

	}

	@Override
	public ActionType getType() {
		return ActionType.HELP;
	}

}
