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

import edu.millersville.cs.segfault.immutable.ImmutableLine;
import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.ObjectType;
import edu.millersville.cs.segfault.model.object.UMLActiveClass;
import edu.millersville.cs.segfault.model.object.UMLClassObject;
import edu.millersville.cs.segfault.model.object.UMLComponent;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.Aggregation;
import edu.millersville.cs.segfault.model.relation.Composition;
import edu.millersville.cs.segfault.model.relation.RelationType;
import edu.millersville.cs.segfault.model.relation.UMLRelation;

public class DrawMode extends PanelInteractionMode {

	/*************************************************************************
	 * The distance within which mouse interactions will snap to snap-points.
	 *************************************************************************/
	public static final int snapDistance = 20;

	// *************************************************************************
	// Private Instance Variables
	// *************************************************************************
	// General drawing variables

	private final UMLPanel panel;
	private final DrawableType drawType;

	private ImmutablePoint lastPoint;
	private ImmutablePoint startPoint;

	// *************************************************************************
	// Constructors
	// *************************************************************************

	/***
	 * Constructs a new DrawMode which interprets mouse and key actions to add
	 * {@link DrawableUML}s into the {@link UMLModel} held by a {@link UMLPanel}
	 * .
	 * 
	 * @param type
	 *            The type of Drawable to add to the panel.
	 * @param panel
	 *            The panel to which the Drawable will be added.
	 */
	public DrawMode(DrawableType type, UMLPanel panel) {
		this.drawType = type;
		this.panel = panel;
		this.lastPoint = null;
		this.startPoint = null;
	}

	// *************************************************************************
	// Object Factories
	// *************************************************************************
	// Returns a UMLObject of subclass ObjectType type
	private UMLObject makeObject(ObjectType type, ImmutablePoint origin,
			Dimension size) {
		
		
			try {
				if (type == ObjectType.CLASS) {
					return new UMLClassObject("", origin, panel.getModel().highestZ() + 1, size, false);
				}
				if (type == ObjectType.ACTIVE_CLASS) {
					return new UMLActiveClass("", origin, panel.getModel().highestZ() + 1, size, false);
				}
				if (type == ObjectType.COMPONENT) {
					return new UMLComponent("", origin, panel.getModel().highestZ() + 1, size, false);
				}
			} catch (Exception e) {
				
			}
		
		
		return new UMLObject("", origin, panel.getModel().highestZ() + 1, size,
				false);
	}

	// Returns a UMLRelation of subclass RelationType type
	private UMLRelation makeRelation(RelationType type, ImmutablePath path) {
		if (type == RelationType.AGGREGATION) {
			return new Aggregation(path, panel.getModel().highestZ() + 1, false);
		}
		if (type == RelationType.COMPOSITION) {
			return new Composition(path, panel.getModel().highestZ() + 1, false);
		}
		return new UMLRelation(path, panel.getModel().highestZ() + 1, false);
	}

	// *************************************************************************
	// Action Listener Methods
	// *************************************************************************

	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		
		if (UMLModel.isRelationType(drawType) || this.isShiftDown()) {
			lastPoint = this.straitSnap(new ImmutablePoint(e.getX(), e.getY()), startPoint, e);
		} else {
			lastPoint = snap(new ImmutablePoint(e.getX(), e.getY()));
		}
		
		panel.repaint();
	}

	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		startPoint = snap(new ImmutablePoint(e.getX(), e.getY()));
	}

	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);

		if (startPoint != null && lastPoint != null) {
			addToModel(startPoint, lastPoint, e);
			startPoint = null;
			lastPoint = null;
		}
	}

	// *************************************************************************
	// Interface Actions
	// *************************************************************************

	private void addToModel(ImmutablePoint first, ImmutablePoint second, MouseEvent e) {
		if (UMLModel.isRelationType(drawType)) {
			// Drawing a relation.

			UMLRelation previous = findByEndPoint(first);
			
			if (previous == null) {
				// This is a new actual relation.
				UMLRelation newRelation = makeRelation(
						UMLModel.getRelationType(drawType),
						new ImmutablePath(first).addLast(this.straitSnap(second, first, e)));
				panel.changeModel(panel.getModel().addRelation(newRelation));

			} else {
				// Adding on to a previous relation
				UMLModel intermediate = panel.getModel().remove(previous);
				UMLRelation newVersion = previous.extend(this.straitSnap(second, first, e));
				intermediate = intermediate.addRelation(newVersion);
				panel.changeModel(intermediate);
			}
			
		} else {

			panel.changeModel(panel.getModel().addObject(
					makeObject(
							UMLModel.getObjectType(drawType),
							new ImmutablePoint(Math.min(first.getX(),
									second.getX()), Math.min(first.getY(),
									second.getY())),
							new Dimension(
									Math.abs(first.getX() - second.getX()),
									Math.abs(first.getY() - second.getY())))));

		}

	}

	// *************************************************************************
	// Drawing Methods
	// *************************************************************************

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
			if ((current != null && closest == null) || current != null
					&& current.distance(p) < closest.distance(p)) {
				closest = current;
			}
		}

		if (closest != null && p.distance(closest) < snapDistance) {
			return closest;
		}
		return p;
	}

	@Override
	public void leaveMode() {
		
	}

	public UMLRelation findByEndPoint(ImmutablePoint p) {
		Iterator<UMLRelation> rIter = panel.getModel().getRelations().iterator();
		UMLRelation highest = null;
				
		while (rIter.hasNext()) {
			UMLRelation current = rIter.next();
			if (Math.abs(current.getEnd().getX() - p.getX()) < snapDistance && 
					Math.abs(current.getEnd().getY() - p.getY()) < snapDistance) {
				if (highest == null || current.getZ() > highest.getZ()) {
					highest = current;
				}
			}
		}
		return highest;
	}

	private ImmutablePoint straitSnap(ImmutablePoint mousePos, ImmutablePoint startPos, MouseEvent e) {
		
		if (e.isShiftDown()) { return this.snap(mousePos); }
		boolean vertical = true;
		int xDistance = Math.abs(mousePos.getX() - startPos.getX());
		int yDistance = Math.abs(mousePos.getY() - startPos.getY());
		if (xDistance > yDistance) {
			vertical = false;
		}
		ImmutablePoint closest = null;
		Iterator<DrawableUML> zIter = panel.getModel().zIterator();
		while (zIter.hasNext()) {
			ImmutablePath currentPath = zIter.next().getPath();
			Iterator<ImmutableLine> lIter = currentPath.lineIterator();
			while (lIter.hasNext()) {
				ImmutableLine currentLine = lIter.next();
				ImmutablePoint currentSnap;
				if (vertical) {
					currentSnap = currentLine.snapAtY(mousePos.getY());
				} else {
					currentSnap = currentLine.snapAtX(mousePos.getX());
				}
				if (currentSnap != null && (closest == null || 
						currentSnap.distance(mousePos) < closest.distance(mousePos))) {
					closest = currentSnap;
				}
			}
			
		}
		if (closest == null) {
			if (vertical) {
				return new ImmutablePoint(startPos.getX(), mousePos.getY());
			} else {
				return new ImmutablePoint(mousePos.getX(), startPos.getY());
			}
		}
		return closest;
	}
}
