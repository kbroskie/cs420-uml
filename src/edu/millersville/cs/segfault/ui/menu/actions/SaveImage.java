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

/**************************************************************************
 * SaveImage is the class responsible for creating the SaveImage action.
 * @author Daniel Rabiega
 *************************************************************************/
public class SaveImage extends AbstractAction implements MenuAction {

	//*************************************************************************
	// Static Instance Variables
	
	private static final long serialVersionUID = 572951439251319138L;
	private UMLWindow parent;
	
	//*************************************************************************
	// Constructors

	/**************************************************************************
	 * Constructor that invokes the method to save the current model as 
	 * a .png file.
	 * @param parent the frame for the interface
	 *************************************************************************/
	public SaveImage(UMLWindow parent) {
		super("Save as Image");
		this.parent = parent;
	}
	
	//*************************************************************************
	// Observers
	
	@Override
	public ActionType getType() { return ActionType.SAVE_IMAGE; }
	
	//*************************************************************************
	// Event Listeners
	
	@Override
	public void actionPerformed(ActionEvent e) {
		UMLPanel panel = parent.getUMLPanel();
		BufferedImage image = (BufferedImage) panel.createImage(panel.getWidth(), panel.getHeight());

		Graphics2D graph = image.createGraphics();
		panel.paintComponent(graph);
		
		// Prompt the user for the location to save the file to.
		JFileChooser dialog = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
		dialog.setFileFilter(filter);
		
		// Save the file if a valid path was given.
		int returnVal = dialog.showSaveDialog(parent);
		if (returnVal==JFileChooser.APPROVE_OPTION) {
			try {
				ImageIO.write(image, "png", dialog.getSelectedFile());
			} catch (Exception ex) {
				System.out.println("Failed saving image.");
			}
		}
	}
}
