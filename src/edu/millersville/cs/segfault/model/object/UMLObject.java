package edu.millersville.cs.segfault.model.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.ui.DrawMode;

public class UMLObject implements DrawableUML {
	
	// De-Serialization helpers
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
	//********************************************************************
	
	
	
	
	private String label;  // Label of the object.
	
	private ImmutablePoint origin;
	private int z;     // Coords of the object.
	private Dimension size;
	private boolean selected; 
	
	//********************************************************************
	// Constructors
	//********************************************************************
	
	// Empty constructor
	public UMLObject(){
		this.label = "";
		this.origin = new ImmutablePoint(0,0);
		this.z = 0;
		this.size = new Dimension(100,100);
		selected = false;
	
	}
	
	// De-serialization constructor
	public UMLObject(String serialized) throws Exception {
		this();
		int startLabel = serialized.indexOf("<label>") + 7;
		int endLabel = serialized.lastIndexOf("</label>");
		if (startLabel == 6 || endLabel == -1) {
			throw new Exception("Object with no label!");
		} else if (startLabel == endLabel) {
			this.label = "";
		} else {
			this.label = serialized.substring(startLabel, endLabel);
		}
			
		this.origin = new ImmutablePoint(findIntAttr("x", serialized), findIntAttr("y", serialized));
		this.z = findIntAttr("z", serialized);
		this.size = new Dimension(findIntAttr("width", serialized), findIntAttr("height", serialized));
	}
	
	// Copy constructor
	public UMLObject(UMLObject source) 
	{
		this();
		this.label = source.getLabel();
		this.origin = source.getOrigin();
		this.z = source.getZ();
		this.size = source.getSize();
	}
	
	public Dimension getSize() {
		return new Dimension(this.size);
	}

	public ImmutablePoint getOrigin() {
		return this.origin;
	}

	// Member constructor
	public UMLObject(String nLabel, ImmutablePoint origin, int nZ, Dimension size, boolean nSelected) 
	{
		this.label = nLabel;
		this.origin = origin;
		this.size = size;
		this.z = nZ;
		this.selected = nSelected;
	}
	
	//********************************************************************
	// Observers
	//********************************************************************
	
	// Returns serialized version of object.
	public String serialize()
	{
		String serialString = "";
		
		serialString += "  <label>" + this.label + "</label>\n";
		
		serialString += "  <x>" + this.origin.getX() + "</x>\n";
		serialString += "  <y>" + this.origin.getY() + "</y>\n";
		serialString += "  <z>" + this.z + "</z>\n";
		serialString += "  <width>" + (int) Math.round(this.size.getWidth()) + "</width>\n";
		serialString += "  <height>" + (int) Math.round(this.size.getHeight()) + "</height>\n";
		
		return serialString;
	}
		
	// Returns object's label.
	public String getLabel()
	{
		return this.label;
	}


	public int getX() { return (int) this.origin.getX(); }
	
	public int getY() { return (int) this.origin.getY(); }
	
	public int getZ() { return this.z; }
	
	public int getWidth() { return (int) this.size.getWidth(); }
	
	public int getHeight() { return (int) this.size.getHeight(); }
	
	public boolean within(ImmutablePoint point) 
	{
		Rectangle2D box = new Rectangle2D.Double(this.getX(), this.getY(),
				this.getWidth(), this.getHeight());
		
		if (box.contains(point.getPoint()))
		{
			return true;
		}
		return false;
	}
	
	//********************************************************************
	// Mutators
	//********************************************************************

	// Change the label.
	public UMLObject changeLabel(String newLabel)
		throws Exception
	{
		return new UMLObject(newLabel, this.origin, this.z, this.size, this.selected);
	}
	
	public UMLObject move(int x, int y, int z) 
		throws Exception
	{
		return new UMLObject(this.label, new ImmutablePoint(x, y), z, this.size, this.selected);
	}

	public UMLObject resize(int width, int height)
		throws Exception
	{
		return new UMLObject(this.label, this.origin, this.z, new Dimension(width, height), this.selected);
	}

	//********************************************************************
	// Custom Drawing
	//********************************************************************
	
	public void draw(Graphics g)
	{
		if (this.selected)
		{
			g.setColor(Color.BLUE);
			g.fillRect(this.getX() - 5, this.getY() - 5, this.getWidth() + 10, this.getHeight() + 10);
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		g.drawString(this.label, this.getX()+15, this.getY()+15);
	}

	public void contextMenu(MouseEvent e) {
		
	}
	
	public DrawableType getType() { return DrawableType.OBJECT; }

	@Override
	public UMLObject select() {
		return new UMLObject(this.label, this.origin, this.z, this.size, true);
	}

	@Override
	public UMLObject unselect() {
		return new UMLObject(this.label, this.origin, this.z, this.size, false);
	}

	@Override
	public ImmutablePoint snapPoint(ImmutablePoint point) {
		ImmutablePoint[] corners = new ImmutablePoint[4];
		Line2D[] lines = new Line2D[4];
		
		corners[0] = new ImmutablePoint(this.getX(), this.getY());
		corners[1] = new ImmutablePoint(this.getX() + this.getWidth(), this.getY());
		corners[2] = new ImmutablePoint(this.getX() + this.getWidth(), 
				this.getY() + this.getHeight());
		corners[3] = new ImmutablePoint(this.getX(), this.getY() + this.getHeight());
		
		lines[0] = new Line2D.Double(corners[0].getPoint(), corners[1].getPoint()); // Top
		lines[1] = new Line2D.Double(corners[1].getPoint(), corners[2].getPoint());
		lines[2] = new Line2D.Double(corners[2].getPoint(), corners[3].getPoint());
		lines[3] = new Line2D.Double(corners[3].getPoint(), corners[0].getPoint());   // Left
		
		int min = 0;
		for (int line = 1; line < 4; ++line) {
			if (lines[min].ptLineDist(point.getPoint()) > 
				lines[line].ptLineDist(point.getPoint())) {
				min = line;
			}
		}
		
		if (lines[min].ptLineDist(point.getPoint()) <= DrawMode.snapDistance) {
			if (min == 0) {
				return new ImmutablePoint(point.getX(), this.getY());
			} else if (min == 1) {
				return new ImmutablePoint(this.getX() + this.getWidth(), point.getY());
			} else if (min == 2) {
				return new ImmutablePoint(point.getX(), this.getY() + this.getHeight());
			} else {
				return new ImmutablePoint(this.getX(), point.getY());
			}
		}
		return null;
	}

	@Override
	public boolean isSelected() {
		return this.selected;
	}

	@Override
	public boolean hit(ImmutablePoint point) {
		return point.getX() > this.origin.getX() &&
			   point.getX() < this.origin.getX() + this.size.getWidth() &&
			   point.getY() > this.origin.getY() && 
			   point.getY() < this.origin.getY() + this.size.getHeight();
		
	}

	@Override
	public boolean isWithin(Rectangle2D dragArea) {
		Rectangle2D me = new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		if (dragArea.contains(me)) {
			return true;
		}
		return false;
	}


}