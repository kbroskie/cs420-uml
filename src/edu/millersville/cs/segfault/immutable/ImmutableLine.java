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
	// Observers
	
	public ImmutablePoint getFirst() {
		return this.firstPoint;
	}
	
	public ImmutablePoint getSecond() {
		return this.secondPoint;
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
		if (firstPoint.getY() != secondPoint.getY()) {
			return ((firstPoint.getX()-secondPoint.getX())*1.0)/
					((firstPoint.getY() - secondPoint.getY())*1.0);
		}
		return 0; // Sadly, used for both 0 and infinite slope.
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
		if (this.slope() != 0) {
			// Find the equation for the perpendicular line
			double newLineSlope = -1/this.slope();
			double newIntercept = p.getY() - newLineSlope * p.getX();
			
			// Find the missing Point
			int newX = (int) Math.floor((this.slope()-newLineSlope)/(newIntercept-this.intercept()));
			int newY = (int) (this.slope()*newX - this.intercept());

			// Make sure that point is within this line segment
			if (newX > Math.min(firstPoint.getX(), secondPoint.getX()) &&
					newX < Math.max(firstPoint.getX(), secondPoint.getX())) {
				return new ImmutableLine(p, new ImmutablePoint(newX, newY));
			} 
		} else {
			// If we get here, one of our lines is horizontal.
			if (firstPoint.getY() == secondPoint.getY()) {
				// We are horizontal.
				if (p.getX() > Math.min(firstPoint.getX(), secondPoint.getX()) ||
						p.getX() < Math.max(firstPoint.getX(), secondPoint.getX()))
				{
					return new ImmutableLine(p, new ImmutablePoint(p.getX(), firstPoint.getY()));
				}
			} else {
				// We are vertical.
				if (p.getY() > Math.min(firstPoint.getY(), secondPoint.getY()) && 
						p.getY() < Math.max(firstPoint.getY(), secondPoint.getY())) {
					return new ImmutableLine(p, new ImmutablePoint(firstPoint.getX(), p.getY()));
				}
			}
		}
		return null; // No line exists that meets these requirements.
	}
	
	public ImmutablePoint snapPoint(ImmutablePoint p) {
		ImmutableLine perpLine = this.perpendicular(p);
		if (perpLine != null) {
			return perpLine.secondPoint;
		}
		
		if (firstPoint.distance(p) < secondPoint.distance(p)) {
			return firstPoint;
		}
		return secondPoint;
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
