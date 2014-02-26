package edu.millersville.cs.segfault.model;

//****************************************************************************
// Import Statement

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Iterator;

/*****************************************************************************
 * Root of the class heirarchy for various types of Relations and arrows.    
 * @author Daniel Rabiega                                                    
 *****************************************************************************/
public class UMLRelation implements DrawableUML {
	
	//*************************************************************************
	// Static Functions
	private static int findIntAttr(String attr, String serialized)
			throws Exception
		{
			int value;
			
			if (!serialized.contains("<" + attr + ">"))
			{
				throw new Exception("Attribute " + attr + "not found in object!");
			} else {
				value = Integer.parseInt(serialized.substring(
							serialized.indexOf("<"+attr+">") + attr.length() + 2,
							serialized.indexOf("</"+attr+">")));
			}
			
			return value;
		}

	
	//*************************************************************************
	// Member variables
	//*************************************************************************
	private Path path;
	private int z;
	private boolean selected;
	
	
	//*************************************************************************
	
	
	
	//*************************************************************************
	// Constructors
	
	/**************************************************************************
	 * Creates a new UMLRelation with a given path                            
	 * @param path The path of the new UMLRelation                            
	 **************************************************************************/
	public UMLRelation(Path path, int z, boolean selected)
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
	public UMLRelation(String serialized)
		throws Exception
	{
		this.selected = false;
		this.z = findIntAttr("z", serialized);
		this.path = new Path(serialized.substring(
				serialized.indexOf("<path>")+6,
				serialized.indexOf("</path>")));
	}
	
	//*************************************************************************
	// Observers
	private String serialize() 
	{
		String relationString = "";

		relationString += "  <z>" + this.z + "</z>\n";
		relationString += path.toString();
		
		return relationString;
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
	public boolean near(Point point, int maxdist)
	{
		if (this.path.getSize() < 2) {
			return false;
		}
		
		Iterator<Point> pIter = this.path.iterator();
		Point first;
		Point second = pIter.next();
		
		while (pIter.hasNext()) {
			first = second;
			second = pIter.next();
			
			Line2D line = new Line2D.Double(first, second);
			if (line.ptLineDist(point) < maxdist) {
				return true;
			}
		}
		
		return false;
	}
	
	
	//*************************************************************************
	// Drawing Methods
	
	public void draw(Graphics g) 
	{
		g.setColor(Color.BLACK);
		if (this.selected) {
			g.setColor(Color.BLUE);
		}
		
		if (this.path.getSize() < 2) {
			return;
		}
		
		Iterator<Point> pIter = this.path.iterator();
		
		Point first;
		Point second = pIter.next();
		
		while (pIter.hasNext()) {
			first = second;
			second = pIter.next();
			
			g.drawLine((int) first.getX(), (int) first.getY(), (int) second.getX(), (int) second.getY());
			if (pIter.hasNext()) {
				g.drawArc((int) second.getX()-5, (int) second.getY()-5, 10, 10, 0, (int) Math.PI*2); 
			}
		}
	}
	
	public DrawableType getType() { return DrawableType.RELATION; }

	@Override
	public UMLRelation select() {
		return new UMLRelation(this.getPath(), this.getZ(), true);
	}

	private Path getPath() {
		return this.path;
	}

	@Override
	public UMLRelation unselect() {
		return new UMLRelation(this.getPath(), this.getZ(), false);
	}

	public String toString() {
		return this.serialize();
	}

	@Override
	public Point snapPoint(int x, int y) {
		
		return this.path.closestPointTo(new Point(x, y));
	}
}
