package edu.millersville.cs.segfault.ui;
/***
 * Implements the PanelInteractionMode to accept user input events and add {@link DrawableUML}
 * objects to the model of the given panel.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.ObjectType;
import edu.millersville.cs.segfault.model.Path;
import edu.millersville.cs.segfault.model.RelationType;
import edu.millersville.cs.segfault.model.UMLObject;
import edu.millersville.cs.segfault.model.UMLRelation;

public class DrawMode implements PanelInteractionMode {

	/***
	 * The distance within which mouse interactions will snap to snap-points.
	 */
	public static final int snapDistance=20;
	
	//*************************************************************************
	// enum translation keys
	private static final DrawableType[] drawableToRelation = {
		DrawableType.RELATION
	};
	
	private static final RelationType[] relationToDrawable = {
		RelationType.RELATION
	};
	
	private static final DrawableType[] drawableToObject = {
		DrawableType.OBJECT
	};
	
	private static final ObjectType[] objectToDrawable = {
		ObjectType.OBJECT
	};
	
	//*************************************************************************
	// Private Instance Variables 
	//*************************************************************************
	// General drawing variables
	private UMLPanel panel;
	private boolean drawing;
	private boolean drawingRelation;  // false when drawing an object
	private Point lastPoint;
	
	// Object drawing variables
	private ObjectType objectType;
	
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
		
		if (isRelationType(type)) {
			drawingRelation=true;
			relationType = getRelationType(type);
		} else {
			drawingRelation=false;
		}
		
		
	}
	
	//*************************************************************************
	// Object Factories
	//*************************************************************************
	// Returns a UMLObject of subclass ObjectType type
	private static UMLObject makeObject(ObjectType type, int x, int y, int z, int width, int height) {

		// Calls to make other object types go here.
		// I.E.
		// if (type == ObjectType.CLASS) {
		// 		return new UMLClass(int x, int y, int z, int width, int height)
		// }
		
		// TODO Once new objects are in.
		// return new UMLObject(x, y, z, width, height);
		return null;		
	}
	// Returns a UMLRelation of subclass RelationType type
	private static UMLRelation makeRelation(RelationType type, Path path) {
		return new UMLRelation(path, -1, false);
	}

	//*************************************************************************
	// Type translation methods
	//*************************************************************************
	private boolean isRelationType(DrawableType dType) {
		for (int i=0; i<drawableToRelation.length; ++i) {
			if (drawableToRelation[i]==dType) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isObjectType(DrawableType dType) {
		for (int i=0; i<drawableToObject.length; ++i) {
			if (drawableToObject[i]==dType) {
				return true;
			}
		}
		return false;
	}
	
	private static RelationType getRelationType(DrawableType dType) {
		for (int i=0; i<drawableToRelation.length; ++i) {
			if (drawableToRelation[i] == dType) {
				return relationToDrawable[i];
			}
		}
		return null;
	}
	
	private static ObjectType getObjectType(DrawableType dType) {
		for (int i=0; i<drawableToObject.length; ++i) {
			if (drawableToObject[i]==dType) {
				return objectToDrawable[i];
			}
		}
		return null;
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
		
		drawPath = new Path(orSnap(e));
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// Draw the relation with ending point as a snap point if one is available
		
		drawPath = drawPath.addPoint(orSnap(e));
		
		
		drawing = false;
		
		try {
			panel.changeModel(panel.currentModel.link(makeRelation(relationType, drawPath)));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
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
			g.drawLine((int) drawPath.first().getX(), (int) drawPath.first().getY(), 
					(int) lastPoint.getX(), (int) lastPoint.getY());
			
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
			Point newSnap = zIter.next().snapPoint(e.getX(), e.getY());
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
	
	
}
