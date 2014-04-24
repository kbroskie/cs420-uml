package edu.millersville.cs.segfault.ui.menu.actions;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.millersville.cs.segfault.ui.UMLPanel;
import edu.millersville.cs.segfault.ui.UMLWindow;
import edu.millersville.cs.segfault.ui.menu.ActionType;
import edu.millersville.cs.segfault.ui.menu.MenuAction;

public class SaveImage extends AbstractAction implements MenuAction {

	private static final long serialVersionUID = 572951439251319138L;
	private UMLWindow parent;
	
	public SaveImage(UMLWindow parent) {
		super("Save as Image");
		this.parent = parent;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		UMLPanel panel = parent.getUMLPanel();
		BufferedImage image = (BufferedImage) panel.createImage(panel.getWidth(), panel.getHeight());
		
		Graphics2D graph = image.createGraphics();
		panel.paintComponent(graph);
		
		JFileChooser dialog = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
		dialog.setFileFilter(filter);
		int returnVal = dialog.showSaveDialog(parent);
		if (returnVal==JFileChooser.APPROVE_OPTION) {
			try {
				ImageIO.write(image, "png", dialog.getSelectedFile());
			} catch (Exception ex) {
				System.out.println("Failed saving image.");
			}
		}
	}

	@Override
	public ActionType getType() {
		return ActionType.SAVE_IMAGE;
	}

}
