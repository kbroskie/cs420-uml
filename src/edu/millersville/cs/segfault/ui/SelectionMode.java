package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import edu.millersville.cs.segfault.model.DrawableUML;

public class SelectionMode implements PanelInteractionMode {

	private UMLPanel panel;
	
	private boolean controlDown;
	private boolean shiftDown;
	
	private boolean perhapsDragging; // Sometimes it's hard to detect drag.
	private Point dragStart;
	
	private Point lastPoint;
	
	public SelectionMode(UMLPanel caller)
	{
		this.panel = caller;
		this.perhapsDragging = false;
		this.controlDown = false;
		this.shiftDown = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		panel.getFocus();
		
		if (!controlDown && !shiftDown) {
			panel.changeModel(panel.model().unselectAll());
		}
		Iterator<DrawableUML> zIter = panel.model().zIterator();
		while (zIter.hasNext()) {
			DrawableUML current = zIter.next();
			if (current.hit(new Point(e.getX(), e.getY()))) {
				panel.changeModel(panel.currentModel.select(current));
				break;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		panel.getFocus();
		
		if (!controlDown && !shiftDown) {
			panel.changeModel(panel.model().unselectAll());
		}
		this.perhapsDragging = true;
		this.dragStart = new Point(e.getX(), e.getY());
		this.lastPoint = this.dragStart;
		panel.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
				
		panel.getFocus();
		
		this.perhapsDragging = false;
		int x1 = (int) dragStart.getX();
		int x2 = e.getX();
		int y1 = (int) dragStart.getY();
		int y2 = e.getY();
		Point dragEnd = new Point(x2, y2);
		Rectangle2D dragArea = new Rectangle2D.Double(Math.min(x1, x2), Math.min(y1, y2),
				Math.abs(x1-x2), Math.abs(y1-y2));
		
		if (dragEnd.distance(dragStart) > 10) {
			Iterator<DrawableUML> zIter = panel.model().zIterator();
			DrawableUML current;
			while (zIter.hasNext()) {
				current = zIter.next();
				if (current.isWithin(dragArea)) {
					panel.changeModel(panel.model().select(current));
				}
			}
		}
		panel.repaint();

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		lastPoint = new Point(e.getX(), e.getY());
		panel.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g) 
	{
		
		if (perhapsDragging) {
			int x1 = (int) dragStart.getX();
			int x2 = (int) lastPoint.getX();
			int y1 = (int) dragStart.getY();
			int y2 = (int) lastPoint.getY();
			
			g.setColor(Color.BLUE);
			g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
		}
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_DELETE)
		{
			panel.changeModel(panel.model().deleteSelected());
		} else if (e.getKeyCode()==KeyEvent.VK_CONTROL) {
			controlDown = true;
		} else if (e.getKeyCode()==KeyEvent.VK_SHIFT) {
			shiftDown = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_CONTROL) {
			controlDown = false;
		} else if (e.getKeyCode()==KeyEvent.VK_SHIFT) {
			shiftDown = false;
		}
	}
	
	public Point orSnap(MouseEvent e) {
		
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
		panel.changeModel(panel.model().unselectAll());
	}
	
}
