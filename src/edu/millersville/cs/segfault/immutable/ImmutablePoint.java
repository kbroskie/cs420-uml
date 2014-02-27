package edu.millersville.cs.segfault.immutable;

import java.awt.Point;

/*****************************************************************************
 * An immutable, integer based Point implementation.
 * @author Daniel Rabiega
 *****************************************************************************/
public class ImmutablePoint {

	//************************************************************************
	// Instance Variables
	
	private final int x;
	private final int y;
	
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
	
	//************************************************************************
	// Observers
	
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
}
