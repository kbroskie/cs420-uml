package edu.millersville.cs.segfault.immutable;

import java.awt.Point;

import edu.millersville.cs.segfault.model.XMLAttribute;

/*****************************************************************************
 * An immutable, integer based Point implementation.
 * @author Daniel Rabiega
 *****************************************************************************/
public class ImmutablePoint {

	//************************************************************************
	// Instance Variables
	
	public final int x;
	public final int y;
	
	//************************************************************************
	// Constructors
	
	/*************************************************************************
	 * Creates a new immutable point at the origin.
	 *************************************************************************/
	public ImmutablePoint() {
		this.x=0;
		this.y=0;
	}
	
	/*************************************************************************
	 * Creates a new immutable point at given coordinates.
	 *************************************************************************/
	public ImmutablePoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public ImmutablePoint(String s) {
		this.x = XMLAttribute.getIntAttribute(s, "x");
		this.y = XMLAttribute.getIntAttribute(s, "y");
	}
	
	//************************************************************************
	// Observers
	
	public String serialize() {
		return XMLAttribute.makeTag("point", 
									XMLAttribute.makeTag("x", this.x) + 
									XMLAttribute.makeTag("y", this.y));	
	}
	
	public String serialize(String name) {
		return XMLAttribute.makeTag(name, 
				XMLAttribute.makeTag("x", this.x) + 
				XMLAttribute.makeTag("y", this.y));	
	}
	
	/*************************************************************************
	 * Returns the X coordinate of this point.
	 *************************************************************************/
	public int getX() {
		return this.x;
	}
	
	/*************************************************************************
	 * Returns the Y coordinate of this point.
	 *************************************************************************/
	public int getY() {
		return this.y;
	}
	
	/*************************************************************************
	 * Returns a Point object with the same coordinates as this point.
	 *************************************************************************/
	public Point getPoint() {
		return new Point(this.x, this.y);
	}
	
	/*************************************************************************
	 * Returns the distance between this point and and another ImmutablePoint
	 *************************************************************************/
	public double distance(ImmutablePoint p) {
		return Math.sqrt((Math.pow(this.x - p.getX(), 2) + 
				          Math.pow(this.y - p.getY(), 2))*1.0);
	}
	
	public String toString() {
		return this.x + "," + this.y;
	}
}
