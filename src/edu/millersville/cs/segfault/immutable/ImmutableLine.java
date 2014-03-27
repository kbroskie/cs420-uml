package edu.millersville.cs.segfault.immutable;

import java.awt.Graphics;

import edu.millersville.cs.segfault.model.XMLAttribute;

/*****************************************************************************
 * An immutable line class.
 * @author Daniel Rabiega
 *****************************************************************************/
public class ImmutableLine {

	//************************************************************************
	// Instance Variables
	
	public final ImmutablePoint first;
	public final ImmutablePoint second;
	
	//************************************************************************
	// Constructors
	
	/*************************************************************************
	 * Creates a new immutable line between two immutable points.
	 *************************************************************************/
	public ImmutableLine(ImmutablePoint first, ImmutablePoint second) {
		this.first = first;
		this.second = second;
	}
	
	public ImmutableLine(String s) {
		this.first = new ImmutablePoint(XMLAttribute.getAttribute(s, "first"));
		this.second = new ImmutablePoint(XMLAttribute.getAttribute(s, "second"));
	}
	
	//************************************************************************
	// Observers
	
	public String serialize() {
		return XMLAttribute.makeTag("line", first.serialize("first")+second.serialize("second"));
	}
	
	public String serialize(String name) {
		return XMLAttribute.makeTag(name, first.serialize("first")+second.serialize("second"));
	}
	
	//************************************************************************
	// Drawing Methods
	
	public void draw(Graphics g) {
		g.drawLine(first.x, first.y, second.x, second.y);
	}
	
	//************************************************************************
	// Math Stuff
	
	/*************************************************************************
	 * Returns the length of this line segment.
	 *************************************************************************/
	public double length() {
		return Math.sqrt((Math.pow(first.getX() - second.getX(), 2) 
				+ Math.pow(first.getY() - second.getY(), 2))*1.0);
	}
	
	/*************************************************************************
	 * Returns the slope of this line segment.
	 *************************************************************************/
	public double slope() {
		if (first.getY() != second.getY() && first.getX() != second.getX()) {
			return ((first.getX()-second.getX())*1.0)/
					((first.getY() - second.getY())*1.0);
		}
		return 0; // Sadly, used for both 0 and infinite slope.
	}
	/*************************************************************************
	 * Returns the y intercept of this line segment.
	 *************************************************************************/
	public double intercept() {
		return first.getY() - this.slope() * first.getX();
	}
	
	/*************************************************************************
	 * Returns a perpendicular line attached to this line segment through
	 * point p if one exists, null otherwise.
	 *************************************************************************/
	public ImmutableLine perpendicular(ImmutablePoint p) {
		if (this.slope() != 0) {
			// Find the equation for the perpendicular line
			double newLineSlope = -1/this.slope();
			double newIntercept = p.getY() - newLineSlope * p.getX();
			
			// Find the missing Point
			int newX = (int) Math.floor((this.slope()-newLineSlope)/(newIntercept-this.intercept()));
			int newY = (int) (this.slope()*newX - this.intercept());

			// Make sure that point is within this line segment
			if (newX > Math.min(first.getX(), second.getX()) &&
					newX < Math.max(first.getX(), second.getX())) {
				return new ImmutableLine(p, new ImmutablePoint(newX, newY));
			} 
		} else {
			// If we get here, one of our lines is horizontal.
			if (first.getY() == second.getY()) {
				// We are horizontal.
				if (p.getX() > Math.min(first.getX(), second.getX()) ||
						p.getX() < Math.max(first.getX(), second.getX()))
				{
					return new ImmutableLine(p, new ImmutablePoint(p.getX(), first.getY()));
				}
			} else {
				// We are vertical.
				if (p.getY() > Math.min(first.getY(), second.getY()) && 
						p.getY() < Math.max(first.getY(), second.getY())) {
					return new ImmutableLine(p, new ImmutablePoint(first.getX(), p.getY()));
				}
			}
		}
		return null; // No line exists that meets these requirements.
	}
	
	public ImmutablePoint snapPoint(ImmutablePoint p) {
		ImmutableLine perpLine = this.perpendicular(p);
		if (perpLine != null) {
			return perpLine.second;
		}
		
		if (first.distance(p) < second.distance(p)) {
			return first;
		}
		return second;
	}

	public ImmutablePoint snapAtX(int x) {
		if (x >= Math.max(first.getX(), second.getX()) &&
			x <= Math.min(first.getX(), second.getX())) {
				if (this.slope() == 0 ) {
					if (first.getY() == second.getY()) {
						return new ImmutablePoint(x, first.getY());
					}
				} else {
					return new ImmutablePoint(x, (int) Math.round((x * this.slope())+this.intercept()));
				}
			}
		return null;
	}
	
	
	public ImmutablePoint snapAtY(int y) {
		if (y >= Math.max(first.getY(), second.getY()) &&
				y <= Math.min(first.getY(), second.getY())) {
			
			if (this.slope()==0) {
				if (first.getX() == second.getX()) {
					return new ImmutablePoint(first.getX(), y);
				}
			} else {
				return new ImmutablePoint((int) Math.round((y - this.intercept())/this.slope()),y);
			}
			
		}
		
		return null;
	}
	
	/*************************************************************************
	 * Returns the minimum distance between this line segment and a point.
	 *************************************************************************/
	public double distance(ImmutablePoint point) {
		ImmutableLine perpLine = this.perpendicular(point);
		if (perpLine != null) {
			return perpLine.length();
		}
		return Math.min(first.distance(point), second.distance(point));
	}

}
