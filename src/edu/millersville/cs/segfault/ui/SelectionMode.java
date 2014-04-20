package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.immutable.ImmutableSet;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.UMLRelation;

/*****************************************************************************
 * Interprets user input and uses it to select and deselect items in the 
 * current diagram.
 * 
 * @author Daniel Rabiega
 */

public class SelectionMode extends PanelInteractionMode {

	//************************************************************************
	// Instance Variables
	
	private enum GestureType { UNKNOWN, DRAG_SELECT, SCALE, RELOCATE, TRANSLATE }
	
	private final UMLWindow parent;
	private static final int handleSize = 5;	
	
	private ImmutablePoint gestureStartPoint;
	private ImmutablePoint gestureLastPoint;
	
	private DrawableUML    gestureStartTarget;
	private DrawableUML    gestureLastTarget;
	
	private GestureType    gestureType;
	private int            gestureHandlePos;
	
	
	//************************************************************************
	// Constructors
	
	/*************************************************************************
	 * Creates a new Selection Mode.
	 * 
	 * @param caller The UMLPanel in which to select and deselect items.
	 */
 	public SelectionMode(UMLWindow parent)
	{
		this.parent = parent;
		
		this.gestureLastPoint = null;
	}
	
	//************************************************************************
	// Action Listeners
	
	@Override
	public void mousePressed(MouseEvent e) {
				
		UMLPanel panel = parent.getUMLPanel();
		UMLModel model = panel.getModel();
		
		this.gestureType = GestureType.UNKNOWN;
		
		this.gestureStartPoint = ImmutablePoint.toPoint(e);
		if (handleClicked(e)) {
			startHandleDrag(e);
			return;
		}
		this.gestureStartTarget = hit(e);
		if (this.gestureStartTarget == null) {
			this.gestureType = GestureType.DRAG_SELECT;
			return;
		}
		if (this.gestureStartTarget.isSelected()) {
			this.gestureType = GestureType.TRANSLATE;
		} else {
			try {
				UMLModel workingModel = parent.getUMLPanel().getModel();
				if (!e.isShiftDown() && !e.isControlDown()) {
					workingModel = workingModel.unselectAll();
				}
				workingModel = workingModel.select(this.gestureStartTarget);
				parent.getUMLPanel().changeModel(workingModel);
			} catch (Exception ex) {
				System.out.println("Selection change failed.");
			}
		}
		
		
	}
	
	public void mouseDragged(MouseEvent e) {
		this.gestureLastPoint = ImmutablePoint.toPoint(e);
		if (this.gestureType==GestureType.SCALE) {
			scale(e);
		}		
		if (this.gestureType==GestureType.TRANSLATE) {
			translateItems();
		}
		if (this.gestureType==GestureType.RELOCATE) {
			relocate(e);
		}

		parent.getUMLPanel().repaint();
	}
	
	public void mouseReleased(MouseEvent e) {
		
		if (this.gestureType == GestureType.DRAG_SELECT) {
			dragSelect(e);
		} 
		
		if (this.gestureType == GestureType.SCALE || 
			this.gestureType == GestureType.TRANSLATE ||
			this.gestureType == GestureType.RELOCATE) {
			parent.getUMLPanel().changeModel(parent.getUMLPanel().getModel());
		}
		
		this.gestureType = GestureType.UNKNOWN;
	}

	private void scale(MouseEvent e) {
		int deltaX = this.gestureLastPoint.x - this.gestureStartPoint.x;
		int deltaY = this.gestureLastPoint.y - this.gestureStartPoint.y;
		
		UMLObject scaledObject = (UMLObject) this.gestureStartTarget;
		
		
		if (this.gestureHandlePos == 0 || this.gestureHandlePos == 1 || this.gestureHandlePos == 2) {
			scaledObject = scaledObject.translate(0, deltaY);
			deltaY = -1 * deltaY;
		}
		if (this.gestureHandlePos == 0 || this.gestureHandlePos == 3 || this.gestureHandlePos == 5) {
			scaledObject = scaledObject.translate(deltaX, 0);
			deltaX = -1 * deltaX;
		}
		
		if (this.gestureHandlePos != 1 && this.gestureHandlePos != 6) {
			scaledObject = scaledObject.scale(deltaX, 0);
		}
		if (this.gestureHandlePos != 3 && this.gestureHandlePos != 4) {
			scaledObject = scaledObject.scale(0, deltaY);
		}
		
		try {
			UMLModel model = parent.getUMLPanel().getModel();
			model = model.remove(this.gestureLastTarget);
			model = model.add(scaledObject);
			parent.getUMLPanel().minorChange(model);
			this.gestureLastTarget = scaledObject;
			parent.getUMLPanel().repaint();
		} catch (Exception ex) {
			System.out.println("Scaling failed.");
		}
		
	}
	
	private void translateItems() {
		int deltaX = this.gestureLastPoint.x - this.gestureStartPoint.x;
		int deltaY = this.gestureLastPoint.y - this.gestureStartPoint.y;
		
		
		for (DrawableUML drawable: parent.getUMLPanel().getModel().selectedSet()) {
			try {
				UMLPanel panel = parent.getUMLPanel();
				UMLModel workingModel = panel.getModel();
				DrawableUML newDrawable = drawable.translate(deltaX, deltaY);
				workingModel = workingModel.remove(drawable);
				workingModel = workingModel.add(newDrawable);
				panel.minorChange(workingModel);
			} catch (Exception e) {
				
			}
		}
		
		this.gestureStartPoint = this.gestureLastPoint;
		
	}
	
	private void dragSelect(MouseEvent e) {
		if (this.gestureStartPoint == null || this.gestureLastPoint == null) { return; }
		int x = Math.min(this.gestureStartPoint.x, this.gestureLastPoint.x);
		int y = Math.min(this.gestureStartPoint.y, this.gestureLastPoint.y);
		int width = Math.max(this.gestureStartPoint.x, this.gestureLastPoint.x) - x;
		int height = Math.max(this.gestureStartPoint.y, this.gestureLastPoint.y) - y;
		Rectangle2D.Double box = new Rectangle2D.Double(x, y, width, height);
		try {
			UMLModel workingModel = parent.getUMLPanel().getModel();

			if (!e.isControlDown() && !e.isShiftDown()) {
				workingModel = workingModel.unselectAll();
			}
			
			
			for (DrawableUML drawable: parent.getUMLPanel().getModel()) {
				if (drawable.isWithin(box)) {
					try {
						workingModel = workingModel.select(drawable);
					} catch (Exception ex) {
						System.out.println("Failed to select.");
					}
				}
			}
			parent.getUMLPanel().changeModel(workingModel);
		} catch (Exception ex) {
			
		}
	}
	
	private boolean handleClicked(MouseEvent e) {
		ImmutablePoint coord = ImmutablePoint.toPoint(e);
		for (ImmutablePoint p: handles(parent.getUMLPanel().getModel())) {
			if (p.distance(coord) < handleSize) {
				return true;
			}
		}
		return false;
	}
	
	private DrawableUML handleOwnerAt(ImmutablePoint p) {
		for (DrawableUML drawable: parent.getUMLPanel().getModel()) {
			for (ImmutablePoint point: handles(drawable)) {
				if (point.distance(p) < handleSize) {
					return drawable;
				}
			}
		}
		return null;
	}
	
	private void startHandleDrag(MouseEvent e) {
		ImmutablePoint handlePoint = ImmutablePoint.toPoint(e);
		DrawableUML owner = handleOwnerAt(handlePoint);
		
		if (!owner.getType().isObject) {
			this.gestureType = GestureType.RELOCATE;
			this.gestureStartTarget = owner;
			return;
		}
		this.gestureType=GestureType.SCALE;
		this.gestureStartTarget = owner;
		this.gestureLastTarget = owner;
		UMLObject oOwner = (UMLObject) owner;
		this.gestureHandlePos = oOwner.nearestCorner(handlePoint);
		
	}
	
	private void relocate(MouseEvent e) {
		
		ImmutablePoint currentPoint = ImmutablePoint.toPoint(e);
		
		int deltaX = this.gestureLastPoint.x - this.gestureStartPoint.x;
		int deltaY = this.gestureLastPoint.y - this.gestureStartPoint.y;
		
		try {
			UMLModel newModel = parent.getUMLPanel().getModel();
			UMLRelation oldRelation = (UMLRelation) this.gestureStartTarget;
			UMLRelation newRelation = oldRelation.translate(oldRelation.nearest(currentPoint), deltaX, deltaY);
			newModel = newModel.remove(oldRelation);
			newModel = newModel.addRelation(newRelation);
			this.gestureStartTarget = newRelation;
			this.gestureStartPoint = this.gestureLastPoint;
			parent.getUMLPanel().minorChange(newModel);
		} catch (Exception ex) {
			System.out.println("Failed relocating relation point!");
		}
		parent.getUMLPanel().repaint();
	}

	private DrawableUML hit(MouseEvent e) {
		ImmutablePoint coord = ImmutablePoint.toPoint(e);
		DrawableUML target = null;
		
		for (DrawableUML drawable: parent.getUMLPanel().getModel()) {
			if (drawable.hit(coord)) {
				target = drawable;
			}
		}
		
		return target;
	}
	
	//************************************************************************
	// Drawing Methods
	
	@Override
	public void draw(Graphics g) 
	{
		UMLPanel panel = parent.getUMLPanel();
		ImmutableSet<UMLObject> selectedObjects = panel.getModel().selectedObjectSet();
		for (UMLObject o: selectedObjects) {
			for (ImmutablePoint p : handles(o)) {
				drawObjectHandle(g, p);
			}
		}
		ImmutableSet<UMLRelation> selectedRelations = panel.getModel().selectedRelationSet();
		for (UMLRelation o: selectedRelations) {
			for (ImmutablePoint p: handles(o)) {
				drawRelationHandle(g, p);
			}
		}
		
		if (this.gestureType == GestureType.DRAG_SELECT && this.gestureLastPoint != null) {
				dragBox(g);
		}
		
	}
	
	private void dragBox(Graphics g) {
		g.setColor(Color.BLUE);
		int x = Math.min(this.gestureStartPoint.x, this.gestureLastPoint.x);
		int y = Math.min(this.gestureStartPoint.y, this.gestureLastPoint.y);
		int width = Math.max(this.gestureStartPoint.x, this.gestureLastPoint.x) - x;
		int height = Math.max(this.gestureStartPoint.y, this.gestureLastPoint.y) - y;
		g.drawRect(x, y, width, height);
	}
	
	private HashSet<ImmutablePoint> handles(UMLObject object) {
		HashSet<ImmutablePoint> handles = new HashSet<>();
		handles.add(object.origin);
		handles.add(new ImmutablePoint(object.origin.x + object.size.width/2, object.origin.y));
		handles.add(new ImmutablePoint(object.origin.x, object.getCorner().y));
		handles.add(new ImmutablePoint(object.origin.x, object.origin.y + object.size.height/2));
		handles.add(new ImmutablePoint(object.getCorner().x, object.origin.y + object.size.height/2));
		handles.add(new ImmutablePoint(object.getCorner().x, object.origin.y));
		handles.add(new ImmutablePoint(object.origin.x + object.size.width/2, object.getCorner().y));
		handles.add(object.getCorner());
		return handles;
	}
	
	private HashSet<ImmutablePoint> handles(UMLRelation relation) {
		HashSet<ImmutablePoint> handles = new HashSet<>();
		for (ImmutablePoint p : relation.getPath()) {
			handles.add(p);
		}
		return handles;
	}
	
	private HashSet<ImmutablePoint> handles(DrawableUML drawable) {
		if (drawable.getType().isObject) { return handles((UMLObject) drawable); }
		return handles((UMLRelation) drawable);
	}
	
	private HashSet<ImmutablePoint> handles(UMLModel model) {
		HashSet<ImmutablePoint> handles = new HashSet<>();
		for (DrawableUML drawable: model.selectedSet()) {
			handles.addAll(handles(drawable));
		}
		return handles;
	}
	
	private void drawObjectHandle(Graphics g, ImmutablePoint p) {
		g.setColor(Color.WHITE);
		g.fillRect(p.x-handleSize, p.y-handleSize, handleSize*2, handleSize*2);
		g.setColor(Color.BLUE);
		g.drawRect(p.x-handleSize, p.y-handleSize, handleSize*2, handleSize*2);
	}
	
	private void drawRelationHandle(Graphics g, ImmutablePoint p) {
		g.setColor(Color.WHITE);
		g.fillArc(p.x-handleSize, p.y-handleSize, handleSize*2, handleSize*2, 0, 360);
		g.setColor(Color.BLACK);
		g.drawArc(p.x-handleSize, p.y-handleSize, handleSize*2, handleSize*2, 0, 360);
	}
	
	
}
