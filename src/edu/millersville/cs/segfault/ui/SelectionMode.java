package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableUML;

public class SelectionMode implements PanelInteractionMode {

	private UMLPanel panel;
	
	private boolean controlDown;
	private boolean shiftDown;
	
	private boolean perhapsDragging; // Sometimes it's hard to detect drag.
	private ImmutablePoint dragStart;
	
	private ImmutablePoint lastImmutablePoint;
	
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
		DrawableUML currentTarget = null;
		while (zIter.hasNext()) {
			
			DrawableUML current = zIter.next();
			if (current.hit(new ImmutablePoint(e.getX(), e.getY()))) {
				currentTarget = current;
			}
		}
		if (currentTarget != null) {
			panel.changeModel(panel.currentModel.select(currentTarget));
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		panel.getFocus();
		
		if (!controlDown && !shiftDown) {
			panel.changeModel(panel.model().unselectAll());
		}
		this.perhapsDragging = true;
		this.dragStart = new ImmutablePoint(e.getX(), e.getY());
		this.lastImmutablePoint = this.dragStart;
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
		ImmutablePoint dragEnd = new ImmutablePoint(x2, y2);
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
		lastImmutablePoint = new ImmutablePoint(e.getX(), e.getY());
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
			int x2 = (int) lastImmutablePoint.getX();
			int y1 = (int) dragStart.getY();
			int y2 = (int) lastImmutablePoint.getY();
			
			g.setColor(Color.BLUE);
			g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
		}
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_CONTROL) {
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
	
	public ImmutablePoint orSnap(MouseEvent e) {
		
		ImmutablePoint mouseImmutablePoint = new ImmutablePoint(e.getX(), e.getY());
		ImmutablePoint snapImmutablePoint = null;
		
		for (Iterator<DrawableUML> zIter = panel.currentModel.zIterator();
				zIter.hasNext();) {
			ImmutablePoint newSnap = zIter.next().snapPoint(new ImmutablePoint(e.getX(), e.getY()));
			if (newSnap != null && snapImmutablePoint == null) {
				snapImmutablePoint = newSnap;
			} else if (newSnap != null){
				if (newSnap.distance(mouseImmutablePoint) < snapImmutablePoint.distance(mouseImmutablePoint)) {
					snapImmutablePoint = newSnap;
				}
			}
		}
		
		if (snapImmutablePoint != null) {
			return snapImmutablePoint;
		}
		return new ImmutablePoint(e.getX(), e.getY());
	}

	@Override
	public void leaveMode() {
		panel.changeModel(panel.model().unselectAll());
	}
	
}
