package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;

import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.ObjectType;
import edu.millersville.cs.segfault.model.Path;
import edu.millersville.cs.segfault.model.RelationType;
import edu.millersville.cs.segfault.model.UMLObject;
import edu.millersville.cs.segfault.model.UMLRelation;

public class DrawMode implements MouseListener, MouseMotionListener, KeyListener, PanelInteractionMode {

	public static final int snapDistance=20;
	
	public static final DrawableType[] drawableToRelation = {
		DrawableType.RELATION
	};
	
	public static final RelationType[] relationToDrawable = {
		RelationType.RELATION
	};
	
	
	DrawableType type;
	UMLPanel panel;
	
	boolean drawing;
	boolean drawingRelation;
	
	Path drawPath;
	
	RelationType relationType;
	ObjectType objectType;
	
	Point lastPoint;
	
	public DrawMode(DrawableType type, UMLPanel panel) {
		this.type = type;
		this.panel = panel;
		
		drawing = false;
		drawingRelation = false;
		
		if (isRelationType(type)) {
			drawingRelation=true;
			relationType = getRelationType(type);
		}
		
		
	}
	
	public static UMLObject makeObject(ObjectType type, int x, int y, int z, int width, int height) {

		// Calls to make other object types go here.
		// I.E.
		// if (type == ObjectType.CLASS) {
		// 		return new UMLClass(int x, int y, int z, int width, int height)
		// }
		
		// TODO Once new objects are in.
		// return new UMLObject(x, y, z, width, height);
		return null;		
	}
	
	public static UMLRelation makeRelation(RelationType type, Path path) {
		return new UMLRelation(path);
	}

	public boolean isRelationType(DrawableType dType) {
		if (dType == DrawableType.RELATION) {
			return true;
		}
		return false;
	}
	
	public static RelationType getRelationType(DrawableType dType) {
		for (int i=0; i<drawableToRelation.length; ++i) {
			if (drawableToRelation[i] == dType) {
				return relationToDrawable[i];
			}
		}
		return null;
	}
	
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

	@Override
	public void draw(Graphics g) {
		if (drawing) {
			g.setColor(Color.BLUE);
			g.drawLine((int) drawPath.first().getX(), (int) drawPath.first().getY(), 
					(int) lastPoint.getX(), (int) lastPoint.getY());
			
		}
		
	}
	
	public Point orSnap(MouseEvent e) {
		
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
