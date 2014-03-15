package edu.millersville.cs.segfault.model.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;

public class UMLObject implements DrawableUML {
	
	public static String typeString = "object";
	
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
		if (size.width < 50) {
			size = new Dimension(50, size.height);
		}
		if (size.height < 50) {
			size = new Dimension(size.width, 50);
		}
		
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
		
		
		g.setColor(Color.WHITE);
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		g.setColor(Color.BLACK);
		if (this.selected)
		{
			g.setColor(Color.BLUE);
		}
		g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		g.drawString(this.label, this.getX()+15, this.getY()+15);
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

	public ImmutablePath getPath() {
		int x1 = this.origin.getX();
		int x2 = x1 + this.size.width;
		int y1 = this.origin.getY();
		int y2 = y1 + this.size.height;
		
		ImmutablePath newPath = new ImmutablePath(this.origin);
		newPath = newPath.addLast(new ImmutablePoint(x2, y1));
		newPath = newPath.addLast(new ImmutablePoint(x2, y2));
		newPath = newPath.addLast(new ImmutablePoint(x1, y2));
		newPath = newPath.addLast(this.origin);
		
		return newPath;
	}
	
	@Override
	public ImmutablePoint snapPoint(ImmutablePoint point) {
		int x1 = this.origin.getX();
		int x2 = x1 + this.size.width;
		int y1 = this.origin.getY();
		int y2 = y1 + this.size.height;
		
		ImmutablePath newPath = new ImmutablePath(this.origin);
		newPath = newPath.addLast(new ImmutablePoint(x2, y1));
		newPath = newPath.addLast(new ImmutablePoint(x2, y2));
		newPath = newPath.addLast(new ImmutablePoint(x1, y2));
		newPath = newPath.addLast(this.origin);
		
		return newPath.snapPoint(point);
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