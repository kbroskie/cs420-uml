package edu.millersville.cs.segfault.ui;
/***
 * Extends PanelInteractionMode to accept user input events and add {@link DrawableUML}
 * objects to the model of the given panel.
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.ObjectType;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.RelationType;
import edu.millersville.cs.segfault.model.relation.UMLRelation;

public class DrawMode extends PanelInteractionMode {

	/*************************************************************************
	 * The distance within which mouse interactions will snap to snap-points.
	 *************************************************************************/
	public static final int snapDistance=20;
	
	//*************************************************************************
	// Private Instance Variables 
	//*************************************************************************
	// General drawing variables

	private final UMLPanel panel;
	private final DrawableType drawType;
	
	private ImmutablePoint lastPoint;
	private ImmutablePoint startPoint;
	
	//*************************************************************************
	// Constructors
	//*************************************************************************	
	
	/***
	 * Constructs a new DrawMode which interprets mouse and key actions to
	 * add {@link DrawableUML}s into the {@link UMLModel} held by a {@link UMLPanel}.
	 * @param type  The type of Drawable to add to the panel.
	 * @param panel The panel to which the Drawable will be added.
	 */	
	public DrawMode(DrawableType type, UMLPanel panel) {
		this.drawType = type;
		this.panel = panel;	
		this.lastPoint = null;
		this.startPoint = null;
	}
	
	//*************************************************************************
	// Object Factories
	//*************************************************************************
	// Returns a UMLObject of subclass ObjectType type
	private UMLObject makeObject(ObjectType type, ImmutablePoint origin, Dimension size) {
		return new UMLObject("", origin, panel.getModel().highestZ() + 1, size, false);
	}
	// Returns a UMLRelation of subclass RelationType type
	private UMLRelation makeRelation(RelationType type, ImmutablePath path) {
		return new UMLRelation(path, panel.getModel().highestZ() + 1, false);
	}

	
	//*************************************************************************
	// Action Listener Methods
	//*************************************************************************

	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		lastPoint = snap(new ImmutablePoint(e.getX(), e.getY()));
		panel.repaint();
	}

	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		startPoint = snap(new ImmutablePoint(e.getX(), e.getY()));
	}

	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		
		if (startPoint != null && lastPoint != null) {
			addToModel(startPoint, lastPoint);
			startPoint = null;
			lastPoint = null;
		}	
	}
	
	//*************************************************************************
	// Interface Actions
	//*************************************************************************
	
	private void addToModel(ImmutablePoint first, ImmutablePoint second) {
		if (UMLModel.isRelationType(drawType)) {
			panel.changeModel(panel.getModel().addRelation(
					makeRelation(UMLModel.getRelationType(drawType),
							     new ImmutablePath(first).addLast(second))));
		} else {
			panel.changeModel(panel.getModel().addObject(
				makeObject(UMLModel.getObjectType(drawType),
					   new ImmutablePoint(Math.min(first.getX(), second.getX()),
							   			  Math.min(first.getY(), second.getY())),
					   new Dimension(Math.abs(first.getX()-second.getX()),
							         Math.abs(first.getY()-second.getY())))));
		}
	}
	
	//*************************************************************************
	// Drawing Methods
	//*************************************************************************
	
	/***
	 * Draws any partially completed objects on it's panel.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		if (startPoint != null && lastPoint != null) {
			if (UMLModel.isObjectType(drawType)) {
				g.drawRect(Math.min(startPoint.getX(), lastPoint.getX()), 
						   Math.min(startPoint.getY(), lastPoint.getY()), 
						   Math.abs(startPoint.getX() - lastPoint.getX()), 
						   Math.abs(startPoint.getY() - lastPoint.getY()));
			} else {
				g.drawLine(startPoint.getX(), startPoint.getY(), 
						   lastPoint.getX(), lastPoint.getY());
			}
		}
	}
	
	public ImmutablePoint snap(ImmutablePoint p) {
		Iterator<DrawableUML> zIter = panel.getModel().zIterator();
		
		ImmutablePoint closest = null;
		
		while (zIter.hasNext()) {
			ImmutablePoint current = zIter.next().snapPoint(p);
			if ((current != null && closest == null) ||
				 current != null && current.distance(p) < closest.distance(p)) {
				closest = current;
			}
		}
		
		if (closest != null && p.distance(closest) < snapDistance) {
			return closest;
		}
		return p;
	}


	@Override
	public void leaveMode() {}
	
	
}
