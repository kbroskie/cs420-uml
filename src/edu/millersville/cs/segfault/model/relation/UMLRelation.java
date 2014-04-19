package edu.millersville.cs.segfault.model.relation;

//****************************************************************************
// Import Statement

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.XMLAttribute;

/*****************************************************************************
 * Root of the class hierarchy for various types of Relations and arrows.    
 * @author Daniel Rabiega                                                    
 *****************************************************************************/
public class UMLRelation implements DrawableUML {
	
	//*************************************************************************
	// Member variables
	//*************************************************************************
	private final ImmutablePath path;
	private final int z;
	private final boolean selected;
	
	//*************************************************************************
	// Constructors
	
	/**************************************************************************
	 * Creates a new UMLRelation with a given path                            
	 * @param path The path of the new UMLRelation                            
	 **************************************************************************/
	public UMLRelation(ImmutablePath path, int z, boolean selected)
	{
		this.z = z;
		this.selected = selected;
		this.path = path;
	}
	
	/*************************************************************************
	 * Creates a new UMLRelation from a string representing a serialized     
	 * UMLRelation                                                           
	 * @param serialized The string representing a serialized UMLRelation
	 * @throws Exception Thrown when it fails to find a mandatory attribute
	 *************************************************************************/
	public UMLRelation(String s)
		throws Exception
	{
		this.z = XMLAttribute.getIntAttribute(s, "z");
		this.path = new ImmutablePath(XMLAttribute.getAttribute(s, "path"));
		this.selected = false;
	}
	
	//*************************************************************************
	// Observers
	public String serialize() 
	{
		return XMLAttribute.makeTag(this.getType().name(), this.toString());
	}
	
	public String toString() {
		return XMLAttribute.makeTag("z", this.z)
			 + XMLAttribute.makeTag("path", this.path.toString());	
	}
	
	/*************************************************************************
	 * Returns the Z attribute of this Relation
	 * Drawing and Click calculation uses this attribute for ordering.
	 *************************************************************************/
	public int getZ() { return this.z; }

	/*************************************************************************
	 * Returns true if point is within maxdist for any segment of the Relation
	 * @param point   The point to test for distance.
	 * @param maxdist Cutoff distance for the test.
	 * @return True when the shortest distance between point and any segment  
	 *         of the relation is less than maxdist. False otherwise.
	 *************************************************************************/
	public boolean near(ImmutablePoint point, int maxdist)
	{
		if (this.path.size() < 2) {
			return false;
		}
		
		Iterator<ImmutablePoint> pIter = this.path.iterator();
		ImmutablePoint first;
		ImmutablePoint second = pIter.next();
		
		while (pIter.hasNext()) {
			first = second;
			second = pIter.next();
			
			Line2D line = new Line2D.Double(first.getPoint(), second.getPoint());
			if (line.ptLineDist(point.getPoint()) < maxdist) {
				return true;
			}
		}
		
		return false;
	}
	
	/**************************************************************************
	 * Returns the path of this relation.
	 */
	public ImmutablePath getPath() {
		return this.path;
	}
	
	/**************************************************************************
	 * Returns the endpoint of this relation.
	 */
	public ImmutablePoint getEnd() {
		return this.path.last();
	}
	
	public ImmutablePoint nearest(ImmutablePoint p) { return this.path.nearest(p); }
	
	//*************************************************************************
	// Mutators
	
	public UMLRelation extend(ImmutablePoint p) {
		return new UMLRelation(this.path.addLast(p), this.z, this.selected);
	}
	
	public UMLRelation translate(int deltaX, int deltaY) {
		UMLRelation newRelation = this;
		try {
			newRelation = DrawableType.makeRelation(this.getType(), this.path.translate(deltaX, deltaY), 
													this.z, this.selected);
		} catch (Exception e) {
			
		}
		return newRelation;
	}
	
	public UMLRelation translate(ImmutablePoint p, int deltaX, int deltaY) {
		UMLRelation newRelation = this;
		try {
			newRelation = DrawableType.makeRelation(this.getType(), this.path.translate(p, deltaX, deltaY), 
													this.z, this.selected);
		} catch (Exception e) {
			
		}
		return newRelation;
	}
	
	//*************************************************************************
	// Drawing Methods
	
	public void draw(Graphics g) 
	{
		this.drawPath(g);
		this.drawArrow(g);
	}
	
	public void drawPath(Graphics g) {
		g.setColor(Color.BLACK);
		if (this.selected) {
			g.setColor(Color.BLUE);
		}
		
		if (this.path.size() < 2) {
			return;
		}
		
		Iterator<ImmutablePoint> pIter = this.path.iterator();
		
		ImmutablePoint first;
		ImmutablePoint second = pIter.next();
		
		while (pIter.hasNext()) {
			first = second;
			second = pIter.next();
			
			g.drawLine((int) first.getX(), (int) first.getY(), (int) second.getX(), (int) second.getY());
			if (!pIter.hasNext()) {
				
			}
		}
	}
	
	public void drawArrow(Graphics g) {
	}
	
	public void showContextMenu(MouseEvent e) {
		
	}
	
	public DrawableType getType() { return DrawableType.RELATION; }

	@Override
	public UMLRelation select() {
		return new UMLRelation(this.getPath(), this.getZ(), true);
	}

	

	@Override
	public UMLRelation unselect() {
		return new UMLRelation(this.getPath(), this.getZ(), false);
	}

	@Override
	public ImmutablePoint snapPoint(ImmutablePoint point) {
		return this.path.snapPoint(point);
	}

	@Override
	public boolean isSelected() {
		return this.selected;
	}

	@Override
	public boolean hit(ImmutablePoint point) {
		return near(point, 10);
	}

	@Override
	public boolean isWithin(Rectangle2D dragArea) {
		Iterator<ImmutablePoint> iter = this.path.iterator();
		ImmutablePoint current;
		while (iter.hasNext()) {
			current = iter.next();
			
			if (!dragArea.contains(current.getPoint())) {
				return false;
			}
		}
		return true;
	}
}
