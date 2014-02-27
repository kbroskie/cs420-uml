package edu.millersville.cs.segfault.ui;
/***
 * Implements the PanelInteractionMode to accept user input events and add {@link DrawableUML}
 * objects to the model of the given panel.
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.Path;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.ObjectType;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.RelationType;
import edu.millersville.cs.segfault.model.relation.UMLRelation;

public class DrawMode implements PanelInteractionMode {

	/*************************************************************************
	 * The distance within which mouse interactions will snap to snap-points.
	 *************************************************************************/
	public static final int snapDistance=20;
	
	
	
	//*************************************************************************
	// Private Instance Variables 
	//*************************************************************************
	// General drawing variables
	private UMLPanel panel;
	private boolean drawing;
	private boolean drawingRelation;  // false when drawing an object
	private Point lastPoint;
	
	// Object drawing variables
	//private ObjectType objectType;
	private Point startPoint;
	
	// Relation drawing variables
	private Path drawPath;
	private RelationType relationType;
	
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
		this.panel = panel;
		
		drawing = false;
		drawingRelation = false;
		
		if (UMLModel.isRelationType(type)) {
			drawingRelation=true;
			relationType = UMLModel.getRelationType(type);
		} else {
			drawingRelation=false;
			//objectType = UMLModel.getObjectType(type);
		}
		
		
	}
	
	//*************************************************************************
	// Object Factories
	//*************************************************************************
	// Returns a UMLObject of subclass ObjectType type
	private static UMLObject makeObject(ObjectType type, Point origin, int z, Dimension size) {
		return new UMLObject("", origin, z, size, false);
	}
	// Returns a UMLRelation of subclass RelationType type
	private static UMLRelation makeRelation(RelationType type, Path path) {
		return new UMLRelation(path, -1, false);
	}

	
	//*************************************************************************
	// Action Listener Methods
	//*************************************************************************
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		drawing = true;
		if (drawingRelation) {
			drawPath = new Path(orSnap(e));
		} else {
			startPoint = orSnap(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		drawing = false;
		Point releasePoint = orSnap(e);
		
		if (drawingRelation) {
		
			drawPath = drawPath.addPoint(releasePoint);
			try {
				panel.changeModel(panel.currentModel.addRelation(makeRelation(relationType, drawPath)));
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		} else {
			int x1 = (int) startPoint.getX();
			int x2 = (int) releasePoint.getX();
			int y1 = (int) startPoint.getY();
			int y2 = (int) releasePoint.getY();
			
			panel.changeModel(panel.currentModel.addObject(
					makeObject(ObjectType.OBJECT, new Point(Math.min(x1, x2), Math.min(y1, y2)), 1, 
							new Dimension(Math.abs(x1-x2), Math.abs(y1-y2)))));
		}
		panel.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (drawing) {
			
			lastPoint = orSnap(e);
			
			panel.repaint();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		
	}

	
	//*************************************************************************
	// Drawing Methods
	//*************************************************************************
	
	/***
	 * Draws any partially completed objects on it's panel.
	 */
	@Override
	public void draw(Graphics g) {
		if (drawing) {
			g.setColor(Color.BLUE);
			
			if (drawingRelation) {
				g.drawLine((int) drawPath.first().getX(), (int) drawPath.first().getY(), 
						(int) lastPoint.getX(), (int) lastPoint.getY());
			} else {
				int x1 = (int) startPoint.getX();
				int x2 = (int) lastPoint.getX();
				int y1 = (int) startPoint.getY();
				int y2 = (int) lastPoint.getY();
				
				g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
			}
		}
		
	}
	
	//*************************************************************************
	// Helpers
	//*************************************************************************
	
	private Point orSnap(MouseEvent e) {
		
		Point mousePoint = new Point(e.getX(), e.getY());
		Point snapPoint = null;
		
		for (Iterator<DrawableUML> zIter = panel.currentModel.zIterator();
				zIter.hasNext();) {
			Point newSnap = zIter.next().snapPoint(new Point(e.getX(), e.getY()));
			if (newSnap != null && snapPoint == null) {
				snapPoint = newSnap;
			} else if (newSnap != null){
				if (newSnap.distance(mousePoint) < snapPoint.distance(mousePoint)) {
					snapPoint = newSnap;
				}
			}
		}
		
		if (snapPoint != null) {
			return snapPoint;
		}
		return new Point(e.getX(), e.getY());
	}

	@Override
	public void leaveMode() {
		// TODO Auto-generated method stub
		
	}
	
	
}
