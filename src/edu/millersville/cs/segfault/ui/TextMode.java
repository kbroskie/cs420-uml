package edu.millersville.cs.segfault.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;

public class TextMode extends PanelInteractionMode {

	private final UMLWindow parent;
	private ImmutableLabel editTarget;
	
	
	public TextMode(UMLWindow parent) {
		this.parent = parent;
		editTarget = null;
	}
	
	public void draw(Graphics g) {

	}

	public void mouseClicked(MouseEvent e) {
		for (UMLObject object: parent.getUMLPanel().getModel().getObjects()) {
			if (object.hit(ImmutablePoint.toPoint(e))) {
				try {
					ImmutableLabel[] newTexts = new ImmutableLabel[object.text.length];
					for (int i=0; i<object.text.length; ++i) {
						newTexts[i] = object.text[i].select();
					}
					UMLObject newObject = DrawableType.makeObject(newTexts, object.getType(), 
							object.origin, object.size, object.z, object.selected);
					UMLModel workingModel = parent.getUMLPanel().getModel();
					workingModel = workingModel.remove(object);
					workingModel = workingModel.add(newObject);
					parent.getUMLPanel().minorChange(workingModel);
				} catch (Exception ex) {
					System.out.println("Could not select text!");
				}
			}
		}
	}
	
}
