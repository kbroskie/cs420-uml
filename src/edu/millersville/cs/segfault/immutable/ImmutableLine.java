package edu.millersville.cs.segfault.immutable;

/*****************************************************************************
 * An immutable line class.
 * @author Daniel Rabiega
 *****************************************************************************/
public class ImmutableLine {

	//************************************************************************
	// Instance Variables
	
	private final ImmutablePoint firstPoint;
	private final ImmutablePoint secondPoint;
	
	//************************************************************************
	// Constructors
	
	/*************************************************************************
	 * Creates a new immutable line between two immutable points.
	 *************************************************************************/
	public ImmutableLine(ImmutablePoint first, ImmutablePoint second) {
		this.firstPoint = first;
		this.secondPoint = second;
	}
	
	//************************************************************************
	// Math Stuff
	
	/*************************************************************************
	 * Returns the length of this line segment.
	 *************************************************************************/
	public double length() {
		return Math.sqrt((Math.pow(firstPoint.getX() - secondPoint.getX(), 2) 
				+ Math.pow(firstPoint.getY() - secondPoint.getY(), 2))*1.0);
	}
	/*************************************************************************
	 * Returns the slope of this line segment.
	 *************************************************************************/
	public double slope() {
		return ((firstPoint.getX()-secondPoint.getX())*1.0)/
				((firstPoint.getY() - secondPoint.getY())*1.0);
	}
	/*************************************************************************
	 * Returns the y intercept of this line segment.
	 *************************************************************************/
	public double intercept() {
		return firstPoint.getY() - this.slope() * firstPoint.getX();
	}
	
	/*************************************************************************
	 * Returns a perpendicular line attached to this line segment through
	 * point p if one exists, null otherwise.
	 *************************************************************************/
	public ImmutableLine perpendicular(ImmutablePoint p) {
		double newLineSlope = 1/this.slope();
		double newConstant = p.getY() - newLineSlope * p.getX();
		int newX = (int) Math.floor((this.slope()-newLineSlope)/(newConstant-this.intercept()));
		int newY = (int) (this.slope()*newX - this.intercept());
		
		if (newX > Math.min(firstPoint.getX(), secondPoint.getX()) &&
				newX < Math.max(firstPoint.getX(), secondPoint.getX())) {
			return new ImmutableLine(p, new ImmutablePoint(newX, newY));
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
		return Math.min(firstPoint.distance(point), secondPoint.distance(point));
	}
}
