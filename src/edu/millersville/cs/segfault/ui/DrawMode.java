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

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutableLine;
import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.relation.UMLRelation;


/*****************************************************************************
 * Class responsible for creating a DrawMode.      			 			 	 *
 * @author Daniel Rabiega                                                    *
 *****************************************************************************/
public class DrawMode extends PanelInteractionMode {

	/*************************************************************************
	 * The distance within which mouse interactions will snap to snap-points.*
	 *************************************************************************/
	public static final int snapDistance = 15;

	//*************************************************************************
	// Instance Variables
	
	private final UMLPanel panel;
	private final DrawableType drawType;

	private ImmutablePoint lastPoint;
	private ImmutablePoint startPoint;
	
	//*************************************************************************
	// Constructors
	
	/*****************************************************************************
	 * Constructs a new DrawMode which interprets mouse and key actions to add	 *
	 * {@link DrawableUML}s into the {@link UMLModel} held by a {@link UMLPanel} *
	 * @param type The type of Drawable to add to the panel.					 *
	 * @param panel The panel to which the Drawable will be added.				 *
	 *****************************************************************************/
	public DrawMode(DrawableType type, UMLPanel panel) {
		this.drawType = type;
		this.panel = panel;
		this.lastPoint = null;
		this.startPoint = null;
	}

	// *************************************************************************
	// Action Listeners
	
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		
		if (!drawType.isObject || this.isShiftDown()) {
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

	public void mouseReleased(MouseEvent e)
	{
		super.mouseReleased(e);

		if (startPoint != null && lastPoint != null) {
			try {
				addToModel(startPoint, lastPoint, e);
			} catch (Exception exc) {
				System.out.println(exc.getMessage());
			}
			startPoint = null;
			lastPoint = null;
		}
	}

	// *************************************************************************
	// Interface Actions
	
	private void addToModel(ImmutablePoint first, ImmutablePoint second, MouseEvent e) 
		throws Exception
	{
		if (!drawType.isObject) {
			// Drawing a relation.
			UMLRelation previous = findByEndPoint(first);
			
			if (previous == null) {
				// This is a new actual relation.
				UMLRelation newRelation = DrawableType.makeRelation(
						drawType,
						new ImmutablePath(first).addLast(this.straitSnap(second, first, e)), 
						panel.getModel().highestZ(), false);
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
					DrawableType.makeObject(
							blankLabel(), 
							drawType,
							new ImmutablePoint(Math.min(first.getX(),
									second.getX()), Math.min(first.getY(),
									second.getY())),
							new Dimension(
									Math.abs(first.getX() - second.getX()),
									Math.abs(first.getY() - second.getY())),
									panel.getModel().highestZ()+1, false)));

		}

	}
	
	/*************************************************************************
	 * Constructs an immutable blank label.
	 * @return an immmutable blank label.
	 *************************************************************************/
	public ImmutableLabel[] blankLabel() {
		ImmutableLabel[] blank = new ImmutableLabel[drawType.textQuantity];
		for (int i=0; i<blank.length; ++i) {
			blank[i] = new ImmutableLabel("");
		}
		return blank;
	}

	/*************************************************************************
	 * If p is within snapdistance of a snap point the snap point will be    *
	 * returned. Otherwise, p will be returned.								 *
	 *************************************************************************/
	public ImmutablePoint snap(ImmutablePoint p) {
		Iterator<DrawableUML> zIter = panel.getModel().iterator();

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

	private UMLRelation findByEndPoint(ImmutablePoint p) {
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
		Iterator<DrawableUML> zIter = panel.getModel().iterator();
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
	
	// *************************************************************************
	// Drawing Methods

	/*************************************************************************
	 * Draws any partially completed objects on it's panel.					 *
	 *************************************************************************/
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		if (startPoint != null && lastPoint != null) {
			if (drawType.isObject) {
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
}
