package edu.millersville.cs.segfault.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;


/*****************************************************************************
 * Mediates user interaction with text boxes.       			 			 *
 * @author Daniel Rabiega                                                    *
 *****************************************************************************/
public class TextMode extends PanelInteractionMode {
	
	//*************************************************************************
	// Instance Variables
	
	private final UMLWindow parent;
	private DrawableUML editTarget;
	
	private int textIndex;
	private int cursorPos;
	
	//*************************************************************************
	// Constructor

	public TextMode(UMLWindow parent) {
		this.parent = parent;
		this.editTarget = null;
		this.textIndex = 0;
		this.cursorPos = 0;
	}
	
	//*************************************************************************
	// Action Listeners
	
	public void mouseClicked(MouseEvent e) {
		parent.getUMLPanel().requestFocusInWindow();
		if (hit(ImmutablePoint.toPoint(e)) != null) {
			updateSelection(hit(new ImmutablePoint(e)));
		}
	}
	
	public void keyTyped(KeyEvent e) {
		if ((int) e.getKeyChar() == 8) {
			remCharacter(e);
			return;
		}
		addCharacter(e.getKeyChar());
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN && textIndex < editTarget.getText().length) { 
			updateSelection(this.editTarget, this.textIndex + 1); 
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			addCharacter('\n');
			
		}
	}
	
	//*************************************************************************
	// Model Observers
	
	private String currentText() {
		return editTarget.getText(textIndex).text;
	}
	
	private boolean hasSelected(DrawableUML drawable) {
		for (ImmutableLabel label: drawable.getText()) {
			if (label.selected) { return true; }
		}
		return false;
	}
	
	private DrawableUML hit(ImmutablePoint p) {
		for (DrawableUML drawable: parent.getUMLPanel().getModel()) {
			if (drawable.hit(p) && drawable.getType().isObject) {
				return drawable;
			}
		}
		return null;
	}
	
	private String beforeCursor() {
		String labelText = currentText();
		if (cursorPos==0) { return ""; }
		if (cursorPos==labelText.length()) { return labelText; }
		return labelText.substring(0, cursorPos);
	}
	
	private String afterCursor() {
		String labelText = currentText();
		if (cursorPos >= labelText.length()) { return ""; }
		if (cursorPos == 0) { return labelText; }
		return labelText.substring(cursorPos);		
	}
	
	//*************************************************************************
	// Producers
	
	private void updateSelection(DrawableUML drawable) {
		updateSelection(drawable, 0);
	}
	
	private void updateSelection(DrawableUML drawable, int index) {
		if (this.editTarget != null && this.editTarget.getText().length <= index) { 
			return; 
		}
		UMLModel model = parent.getUMLPanel().getModel();
		try {
			model = model.remove(drawable);
			model = deselect(model);
			DrawableUML newDrawable = select(drawable, index);
			model = model.add(newDrawable);
			parent.getUMLPanel().changeModel(model);
			this.editTarget = newDrawable;
			this.textIndex = index;
			this.cursorPos = this.editTarget.getText(index).text.length();
			parent.getUMLPanel().repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ImmutableLabel[] select(ImmutableLabel[] labels, int n) {
		ImmutableLabel[] newLabels = new ImmutableLabel[labels.length];
		for (int label = 0; label < labels.length; ++label) {
			newLabels[label] = labels[label];
			if (label == n) { newLabels[label] = labels[label].select(); }
		}
		return newLabels;
	}
	
	private DrawableUML select(DrawableUML drawable, int n) {
		if (!drawable.getType().isObject) { return drawable; }
		try {
			UMLObject object = (UMLObject) drawable;
			return DrawableType.makeObject(select(drawable.getText(), n), object.getType(), 
					  			   		   object.origin, object.size, object.z, object.selected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return drawable;
	}
	
	private ImmutableLabel deselect(ImmutableLabel label) {
		return new ImmutableLabel(label.text, label.getFont(), false);
	}
	
	private DrawableUML deselect(DrawableUML drawable) {

		ImmutableLabel[] labels = new ImmutableLabel[drawable.getText().length];
		
		for (int label=0; label < drawable.getText().length; ++label) {
			labels[label] = deselect(drawable.getText(label));
		}

		return DrawableType.updateLabel(drawable, labels);
	}

	private UMLModel deselect(UMLModel model) {
		try {
			for (DrawableUML drawable: model) {
				if (hasSelected(drawable)) {
					DrawableUML newDrawable = deselect(drawable);
					model = model.remove(drawable).add(newDrawable);
					if (this.editTarget == drawable) { this.editTarget = newDrawable; }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	private void remCharacter(KeyEvent e) {
		if (cursorPos==0) { return; }
		updateTarget(beforeCursor().substring(0, beforeCursor().length()-1) + 
					 afterCursor());
		--this.cursorPos;
	}
	
	private void updateTarget(String newValue) {
		UMLModel model = parent.getUMLPanel().getModel();
		try {
			DrawableUML drawable = replaceTarget(newValue);
			model = model.remove(editTarget);
			model = model.add(drawable);
			parent.getUMLPanel().changeModel(model);
			editTarget = drawable;
			
		} catch (Exception e) {
			System.out.println("Could not update text!");
		}
	}
	
	private DrawableUML replaceTarget(String newValue) {
		return editTarget.setText(editTarget.getText(textIndex).setText(newValue), textIndex);
	}
	
	private void addCharacter(char c) {
		DrawableUML oldDrawable = this.editTarget;
		ImmutableLabel oldLabel = oldDrawable.getText(textIndex);
		String oldText = oldLabel.text;
		String newText = oldText.substring(0, cursorPos) + c + oldText.substring(cursorPos);
		ImmutableLabel newLabel = new ImmutableLabel(newText, oldLabel.getFont(), oldLabel.selected);
		DrawableUML newDrawable = oldDrawable.setText(newLabel, textIndex);
		try {
			UMLModel newModel = parent.getUMLPanel().getModel();
			newModel = newModel.remove(oldDrawable);
			newModel = newModel.add(newDrawable);
			parent.getUMLPanel().changeModel(newModel);
			parent.getUMLPanel().repaint();
			this.editTarget = newDrawable;
			this.cursorPos += 1;
		} catch (Exception ex) {
			System.out.println("Could not change text!");
		}
	}
	
	private void growObjects(Graphics g) {
		for (UMLObject object: parent.getUMLPanel().getModel().getObjects()) {
			if (object.size.width < object.minimumWidth(g) || 
				object.size.height < object.minimumHeight(g)) {
				
				try {
					Dimension newSize = new Dimension(Math.max(object.size.width, object.minimumWidth(g)),
													  Math.max(object.size.height, object.minimumHeight(g)));
					UMLObject newObject = DrawableType.makeObject(object.text, object.getType(),
																  object.origin, newSize, 
																  object.z, object.selected);
					
					if (this.editTarget == object) { this.editTarget = newObject; }
					
					UMLModel model = parent.getUMLPanel().getModel();
					model = model.remove(object).add(newObject);
					parent.getUMLPanel().minorChange(model);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//*************************************************************************
	// Drawing Methods
	
	public void draw(Graphics g) {
		growObjects(g);	
	}
}
