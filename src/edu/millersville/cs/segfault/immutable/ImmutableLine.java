package edu.millersville.cs.segfault.immutable;

import java.awt.Graphics;

import edu.millersville.cs.segfault.model.XMLAttribute;
import edu.millersville.cs.segfault.ui.DrawMode;

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
	
	/*************************************************************************
	 * Creates a new Immutable Line from a serialized XML representation of one.
	 * @param s
	 */
	public ImmutableLine(String s) {
		this.first = new ImmutablePoint(XMLAttribute.getAttribute(s, "first"));
		this.second = new ImmutablePoint(XMLAttribute.getAttribute(s, "second"));
	}
	
	//************************************************************************
	// Observers
	
	/*************************************************************************
	 * Creates an XML serialization of this object wrapped in <line></line>
	 */
	public String serialize() {
		return XMLAttribute.makeTag("line", first.serialize("first")+second.serialize("second"));
	}
	
	/*************************************************************************
	 * Returns a serialized representation of this object wrapped in an XML tag.
	 * @param name The property name of the tag wrapped around this object
	 * @return An XML serial representation of this object.
	 */
	public String serialize(String name) {
		return XMLAttribute.makeTag(name, first.serialize("first")+second.serialize("second"));
	}
	
	//************************************************************************
	// Drawing Methods
	
	/*************************************************************************
	 * Draws a line with these coordinates on the given graphics object.
	 * @param g The graphics object to draw on.
	 */
	
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
	
	/*************************************************************************
	 * Returns the closest point upon this line to a given point.
	 */
	public ImmutablePoint snapPoint(ImmutablePoint p) {
		ImmutableLine perpLine = this.perpendicular(p);
		if (perpLine != null && perpLine.length() < DrawMode.snapDistance ) {
			return perpLine.second;
		}
		
		ImmutableLine fromFirst  = new ImmutableLine(this.first,  p);
		ImmutableLine fromSecond = new ImmutableLine(this.second, p);
		ImmutableLine closer = fromSecond;
		
		if (fromFirst.length() < fromSecond.length()) { closer = fromFirst; }
		
		if (closer.length() < DrawMode.snapDistance*2) { return closer.first; }
		
		return null;		
	}

	/*************************************************************************
	 * Returns the point along this line at a given x value, if one exists.
	 * @param x The value of y to find a point for.
	 * @return Either null, if the line does not exist at that value of x, or a point with x=x.
	 */
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
	
	/*************************************************************************
	 * Returns the point along this line at a given y value, if one exists.
	 * @param y The value of y to find a point for.
	 * @return Either null, if the line does not exist at that value of y, or a point with y=y.
	 */
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
