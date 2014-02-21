package edu.millersville.cs.segfault.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;

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
	UMLObject target;
	UMLObject source;
	int z;
	boolean selected;
	
	//*************************************************************************
	// Constructors
	public UMLRelation(UMLObject source, UMLObject target)
	{
		this.source = source;
		this.target = target;
		this.z = -1;
		this.selected = false;
	}
	
	public UMLRelation(UMLModel model, String serialized)
		throws Exception
	{
		this.selected = false;
		this.source = model.getObject(findIntAttr("source", serialized));
		this.target = model.getObject(findIntAttr("target", serialized));
		this.z = findIntAttr("z", serialized);
	}
	
	//*************************************************************************
	// Observers
	public String serialize(UMLModel model) 
	{
		String relationString = "";
		
		relationString += "  <source>" + model.getId(source) + "</source>\n";
		relationString += "  <target>" + model.getId(target) + "</target>\n";
		relationString += "  <z>" + this.z + "</z>\n";
		
		return relationString;
	}
	
	public UMLObject getSource() { return this.source; }
	
	public UMLObject getTarget() { return this.target; }
	
	public int getZ() { return this.z; }

	public boolean near(int x, int y, int maxdist)
	{
		int source_x = source.getX();
		int source_y = source.getY();
		int target_x = target.getX();
		int target_y = target.getY();
		Line2D myLine = new Line2D.Double(source_x, source_y, target_x, target_y);
		if (myLine.ptLineDist(x, y) < maxdist)
		{
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g) 
	{
		if (this.selected)
		{
			g.setColor(Color.BLUE);
			g.drawLine(this.source.getX(), this.source.getY()-1,
					this.target.getX(), this.target.getY()-1);
			g.drawLine(this.source.getX(), this.source.getY()+1,
					this.target.getX(), this.target.getY()+1);
		}
		g.setColor(Color.BLACK);
		g.drawLine(this.source.getX(), this.source.getY(),
				this.target.getX(), this.target.getY());
	}

	@Override
	public Point getOrigin() {
		return new Point(this.source.getX(), this.source.getY());
	}

	@Override
	public Point getBound() {

		return new Point(this.target.getX(), this.target.getY());
	}
	
	public int getType() { return DrawableUML.RELATION; }

	@Override
	public void select() {
		this.selected = true;
	}

	@Override
	public void unselect() {
		this.selected = false;
	}
}