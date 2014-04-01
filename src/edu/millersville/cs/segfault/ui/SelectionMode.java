package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableUML;

/*****************************************************************************
 * Interprets user input and uses it to select and deselect items in the 
 * current diagram.
 * 
 * @author Daniel Rabiega
 */

public class SelectionMode extends PanelInteractionMode {

	//************************************************************************
	// Instance Variables
	
	private UMLPanel panel;
	
	private boolean controlDown;
	private boolean shiftDown;
	
	private boolean perhapsDragging; // Sometimes it's hard to detect drag.
	private ImmutablePoint dragStart;
	
	private ImmutablePoint lastImmutablePoint;
	
	//************************************************************************
	// Constructors
	
	/*************************************************************************
	 * Creates a new Selection Mode.
	 * 
	 * @param caller The UMLPanel in which to select and deselect items.
	 */
	public SelectionMode(UMLPanel caller)
	{
		this.panel = caller;
		this.perhapsDragging = false;
		this.controlDown = false;
		this.shiftDown = false;
	}
	
	//************************************************************************
	// Action Listeners
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
		panel.getFocus();
		
		if (!controlDown && !shiftDown) {
			try {
				panel.changeModel(panel.getModel().unselectAll());
			} catch (Exception ex) {
				System.out.println("Could no remove selection:" + ex.getMessage());
			}
		}
		Iterator<DrawableUML> zIter = panel.getModel().zIterator();
		DrawableUML currentTarget = null;
		while (zIter.hasNext()) {
			
			DrawableUML current = zIter.next();
			if (current.hit(new ImmutablePoint(e.getX(), e.getY()))) {
				currentTarget = current;
			}
		}
		if (currentTarget != null) {
		try {
			panel.changeModel(panel.getModel().select(currentTarget));
		} catch (Exception ex) {
			System.out.println("Could not select:" + ex.getMessage());
		}
			
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		panel.getFocus();
		
		if (!controlDown && !shiftDown) {
			try {
				panel.changeModel(panel.getModel().unselectAll());
			} catch (Exception ex) {
				System.out.println("Could not unselect:" + ex.getMessage());
			}
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
			Iterator<DrawableUML> zIter = panel.getModel().zIterator();
			DrawableUML current;
			while (zIter.hasNext()) {
				current = zIter.next();
				if (current.isWithin(dragArea)) {
					try {
						panel.changeModel(panel.getModel().select(current));
					} catch (Exception ex) {
						System.out.println("Could not select:" + ex.getMessage());
					}
				}
			}
		}
		panel.repaint();

	}
	@Override
	public void mouseDragged(MouseEvent e) {
		lastImmutablePoint = new ImmutablePoint(e.getX(), e.getY());
		panel.repaint();
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_DELETE)
		{
			try {
				panel.changeModel(panel.getModel().deleteSelected());
			} catch (Exception ex) {
				System.out.println("Could not delete:" + ex.getMessage());
			}
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

	@Override
	public void leaveMode() {
		try {
			panel.changeModel(panel.getModel().unselectAll());
		} catch (Exception ex) {
			System.out.println("Could not unselect all:" + ex.getMessage());
		}
	}
	
	//************************************************************************
	// Drawing Methods
	
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
	
		
	private ImmutablePoint orSnap(MouseEvent e) {
		
		ImmutablePoint mouseImmutablePoint = new ImmutablePoint(e.getX(), e.getY());
		ImmutablePoint snapImmutablePoint = null;
		
		for (Iterator<DrawableUML> zIter = panel.getModel().zIterator();
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

	
	
}
