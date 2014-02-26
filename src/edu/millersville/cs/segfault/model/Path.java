package edu.millersville.cs.segfault.model;

/*****************************************************************************
 * An immutable sequence container for Points.                               *
 *                                                                           *
 * @author Daniel Rabiega                                                    *
 *****************************************************************************/

//*****************************************************************************
// Imports
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import edu.millersville.cs.segfault.ui.DrawMode;


//*****************************************************************************
// Class Definition
public class Path {
	
	//*************************************************************************
	// Instance Variables
	private final Point[] points;
	
	//*************************************************************************
	// Constructors
	
	/********************************************************************
	 * Constructs a new Path by copying an Points                       *                                          *
	 * @param points The array of Points to copy.                                                    *
	 ********************************************************************/
	public Path(Point[] points) {
		this.points = new Point[points.length];
		
		for (int i=0; i<points.length; ++i) {
			this.points[i] = points[i];
		}
	}
	
	/********************************************************************
	 * Constructs a new Path which contains a single Point.             *
	 * @param origin The starting point of the new Path                 *
	 ********************************************************************/
	public Path(Point origin) {
		points = new Point[1];
		points[0] = origin;
	}

	/********************************************************************
	 * Constructs a Path object from the serialized representation of   *
	 * a Path object.                                                   *
	 * @param serialString The serialized Path to construct from.       *
	 ********************************************************************/
	public Path(String serialString) {
		ArrayList<Point> newPoints = new ArrayList<Point>();
		int searchPosition=0;
		while(serialString.indexOf("<point>", searchPosition) != -1 &&
				serialString.indexOf("</point>", searchPosition) != -1) {
			int pointStart = serialString.indexOf("<point>", searchPosition) + 7;
			int pointEnd = serialString.indexOf("</point>", searchPosition);
			String pointString = serialString.substring(pointStart, pointEnd);
			newPoints.add(deserializePoint(pointString));
			searchPosition = pointEnd + 8;
		}
		Point[] pointArray = new Point[newPoints.size()];
		for (int i=0; i<pointArray.length; ++i ) {
			pointArray[i] = newPoints.get(i);
		}
		this.points = pointArray;
	}
	
	public Path(Path path) {
		this.points = path.getPoints();
	}

	//*************************************************************************
	// Deserialization Helpers
	private Point deserializePoint(String pointString) {
		int x = Integer.parseInt(pointString.substring(0, pointString.indexOf(",")));
		int y = Integer.parseInt(pointString.substring(pointString.indexOf(",")+1));
		return new Point(x, y);
	}
	
	//*************************************************************************
	// Iterators
	
	/*************************************************************************
	 * Returns an iterator which returns the points in this path in order.   *
	 * @return An in-sequence iterator for the contained points.             *
	 *************************************************************************/
	public Iterator<Point> iterator() {
		return new PointIterator(this.points);
	}
	
	//*************************************************************************
	// Mutators
	
	/*************************************************************************
	 * Returns a new Path object which is the same as this one except that   *
	 * it has an additional point at the end of it's Path.                   *
	 * @param newPoint The point to be added to the new Path.                *
	 * @return A new Path with newPoint added.                               *
	 *************************************************************************/
	public Path addPoint(Point newPoint){
		Point[] newPath = Arrays.copyOf(points, points.length + 1);
		newPath[newPath.length-1] = newPoint;
		return new Path(newPath);		
	}
	
	//*************************************************************************
	// Observers
	
	/*************************************************************************
	 * Returns this Path formated as a string.                               *
	 *************************************************************************/
	public String toString() {
		return this.serialize();
	}

	/*************************************************************************
	 * Returns the number of Points in this Path                             *
	 * @return The number of Points in this Path                             *
	 *************************************************************************/
	public int getSize() {
		return points.length;
	}
	
	/*************************************************************************
	 * Returns a new Point array which is a copy of the points in this Path. 
	 * @return A new Point[] 
	 *************************************************************************/
	public Point[] getPoints() {
		Point[] newPoints = new Point[this.points.length];
		for (int i=0; i<this.points.length; ++i) {
			newPoints[i] = this.points[i];
		}
		return newPoints;
	}
	
	/*************************************************************************
	 * Returns a copy of the first point in this Path.                       *
	 * @return A copy of the first point in this Path.                       *
	 *************************************************************************/
	public Point first() {
		return new Point(points[0]);
	}
	
	/*************************************************************************
	 * Returns a copy of the last point in this Path.                        *
	 * @return A copy of the last point in this Path.                        *
	 *************************************************************************/
	public Point last() {
		return new Point(points[points.length-1]);
	}

	/*************************************************************************
	 * Returns the closest point on this Path to a given Point.              *
	 * @param testPoint The point to test for distance.                      *
	 * @return The closest point on this Path to testPoint                   *
	 *************************************************************************/
	public Point closestPointTo(Point testPoint) {
		Point snapPoint = null;
		for (int i=0; i<this.points.length; ++i) {
			if (points[i].distance(testPoint) < DrawMode.snapDistance)  {
				if (snapPoint==null) {
					snapPoint = points[i];
				} else if (snapPoint.distance(testPoint) > points[i].distance(testPoint)) {
					snapPoint = points[i];
				}
			}
		}
		return snapPoint;
	}
	
	//*************************************************************************
	// Serialization functions
	
	private String serialize() {
		String serialString = "<path>";
		for (int i=0; i<points.length; ++i) {
			serialString += serializePoint(i);
		}
		return serialString + "</path>";
	}
	
	private String serializePoint(int index) {
		return "<point>" + (int) points[index].getX() + "," + (int) points[index].getY() + "</point>";
	}
	
	//*************************************************************************
	// Nested Classes
	
	private class PointIterator implements Iterator<Point> {
		
		
		Point[] points;
		int index;
		
		public PointIterator(Point[] points) {
			this.points = points;
			index = 0;
		}
		
		@Override
		public boolean hasNext() {
			return index < points.length;
		}

		@Override
		public Point next() {
			return points[index++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Path is immutable.");
		}
		
	}
	
	
}
