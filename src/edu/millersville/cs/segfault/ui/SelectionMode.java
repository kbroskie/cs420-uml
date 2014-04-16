package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;

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
	}
	
	//************************************************************************
	// Action Listeners
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	
	//************************************************************************
	// Drawing Methods
	
	@Override
	public void draw(Graphics g) 
	{
		
	}
	
	private void drawObjectHandle(Graphics g, ImmutablePoint p) {
		g.setColor(Color.WHITE);
		g.fillRect(p.x-3, p.y-3, 7, 7);
		g.setColor(Color.BLUE);
		g.drawRect(p.x-3, p.y-3, 7, 7);
	}
	
	private void drawRelationHandle(Graphics g, ImmutablePoint p) {
		g.setColor(Color.WHITE);
		g.fillArc(p.x-3, p.y-3, 7, 7, 0, 360);
		g.setColor(Color.BLACK);
		g.drawArc(p.x-3, p.y-3, 7, 7, 0, 360);
	}
	
	
}
