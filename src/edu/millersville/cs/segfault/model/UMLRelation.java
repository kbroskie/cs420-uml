package edu.millersville.cs.segfault.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Iterator;

public class UMLRelation implements DrawableUML {
	
	//*************************************************************************
	// Serialization helpers
	public static int findIntAttr(String attr, String serialized)
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
	
	//*************************************************************************
	// Member variables
	//*************************************************************************
	Path path;
	int z;
	boolean selected;
	
	//*************************************************************************
	// Constructors
	public UMLRelation(Path path)
	{
		this.z = -1;
		this.selected = false;
		this.path = path;
	}
	
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
	public String serialize() 
	{
		String relationString = "";

		relationString += "  <z>" + this.z + "</z>\n";
		relationString += path.serialize();
		
		return relationString;
	}
	
	public int getZ() { return this.z; }

	public boolean near(int x, int y, int maxdist)
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
			if (line.ptLineDist(x, y) < maxdist) {
				return true;
			}
		}
		
		return false;
	}
	
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
	public void select() {
		this.selected = true;
	}

	@Override
	public void unselect() {
		this.selected = false;
	}

	public String toString() {
		return this.serialize();
	}

	@Override
	public Point snapPoint(int x, int y) {
		
		return this.path.snapPoint(new Point(x, y));
	}
}