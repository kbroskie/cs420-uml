package edu.millersville.cs.segfault.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import edu.millersville.cs.segfault.ui.DrawMode;

public class Path {
	
	private final Point[] points;
	
	public Path(Point[] points) {
		this.points = points;
	}
	
	public Path(Point origin) {
		points = new Point[1];
		points[0] = origin;
	}

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
	
	public Point deserializePoint(String pointString) {
		int x = Integer.parseInt(pointString.substring(0, pointString.indexOf(",")));
		int y = Integer.parseInt(pointString.substring(pointString.indexOf(",")+1));
		return new Point(x, y);
	}
	
	public Iterator<Point> iterator() {
		return new PointIterator(this.points);
	}
	
	public Path addPoint(Point newPoint){
		Point[] newPath = Arrays.copyOf(points, points.length + 1);
		newPath[newPath.length-1] = newPoint;
		return new Path(newPath);		
	}
	
	public String serialize() {
		String serialString = "<path>";
		for (int i=0; i<points.length; ++i) {
			serialString += serializePoint(i);
		}
		return serialString + "</path>";
	}
	
	public String serializePoint(int index) {
		return "<point>" + (int) points[index].getX() + "," + (int) points[index].getY() + "</point>";
	}
	
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
	
	public String toString() {
		return this.serialize();
	}

	public int getSize() {
		return points.length;
	}

	public Point first() {
		return points[0];
	}
	
	public Point last() {
		return points[points.length-1];
	}

	public Point snapPoint(Point testPoint) {
		
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
		if (snapPoint != null) {
			return snapPoint;
		}
		return null;
	}
}
