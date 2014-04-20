package edu.millersville.cs.segfault.ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;

public class TextMode extends PanelInteractionMode {

	//private final static int blinkSpeed = 3; // How many seconds between cursor blinking.
	
	private final UMLWindow parent;
	private DrawableUML editTarget;
	
	private int textIndex;
	private int cursorPos;
	
	public TextMode(UMLWindow parent) {
		this.parent = parent;
		this.editTarget = null;
		this.textIndex = 0;
		this.cursorPos = 0;
	}
	
	public void draw(Graphics g) {
		if (this.editTarget != null) {
			if (this.textIndex >= editTarget.getText().length) {
				this.textIndex = editTarget.getText().length - 1;
			}
			if (this.cursorPos >= editTarget.getText()[textIndex].text.length()) {
				this.cursorPos = editTarget.getText(textIndex).text.length();
			}
			ImmutablePoint targetOrigin = editTarget.textPos(textIndex);
			String beforeCursor = this.editTarget.getText(textIndex).text.substring(0, this.cursorPos);
			ImmutableLabel labelBeforeCursor =new ImmutableLabel(beforeCursor);
			int widthBeforeCursor = labelBeforeCursor.getWidth(g);
			ImmutablePoint cursorOrigin = targetOrigin.translate(widthBeforeCursor, 0);
			ImmutablePoint cursorBottom = cursorOrigin.translate(0, labelBeforeCursor.getHeight(g));
			g.drawLine(cursorOrigin.x, cursorOrigin.y, cursorOrigin.x, cursorBottom.y);
		}
	}

	public void mouseClicked(MouseEvent e) {
		parent.getUMLPanel().requestFocusInWindow();
		for (UMLObject object: parent.getUMLPanel().getModel().getObjects()) {
			if (object.hit(ImmutablePoint.toPoint(e))) {
				try {
					ImmutableLabel[] newTexts = new ImmutableLabel[object.text.length];
					for (int i=0; i<object.text.length; ++i) {
						newTexts[i] = object.text[i].select();
					}
					UMLObject newObject = DrawableType.makeObject(newTexts, object.getType(), 
							object.origin, object.size, object.z, object.selected);
					this.editTarget = newObject;
					this.textIndex = 0;
					UMLModel workingModel = parent.getUMLPanel().getModel();
					workingModel = workingModel.remove(object);
					workingModel = workingModel.add(newObject);
					parent.getUMLPanel().minorChange(workingModel);
					parent.getUMLPanel().repaint();
				} catch (Exception ex) {
					System.out.println("Could not select text!");
				}
			}
		}
	}
	
	public void keyTyped(KeyEvent e) {
		if ((int) e.getKeyChar() == 8) {
			remCharacter(e);
			return;
		}
		addCharacter(e.getKeyChar());
	}
	
	
	private void remCharacter(KeyEvent e) {
		String string = getCurrent();
		if (cursorPos < 1) { return; }
		string = string.substring(0, cursorPos-1) + string.substring(cursorPos);
		updateTarget(string);
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
	
	
	private String getCurrent() {
		return editTarget.getText(textIndex).text;
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
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN && textIndex < editTarget.getText().length) { ++this.textIndex; }
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			addCharacter('\n');
			
		}
	}
}
