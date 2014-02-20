package edu.millersville.cs.segfault.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

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
	
	private int x;     //
	private int y;     //
	private int z;     // Coords of the object.
	private int width; //
	private int height; //
	private boolean selected; 
	
	//********************************************************************
	// Constructors
	//********************************************************************
	
	// Empty constructor
	public UMLObject(){
		this.label = "";
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.width = 100;
		this.height = 100;
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
			
		this.x = findIntAttr("x", serialized);
		this.y = findIntAttr("y", serialized);
		this.z = findIntAttr("z", serialized);
		this.width = findIntAttr("width", serialized);
		this.height = findIntAttr("height", serialized);
	}
	
	// Copy constructor
	public UMLObject(UMLObject source) 
	{
		this();
		this.label = source.getLabel();
		this.x = source.getX();
		this.y = source.getY();
		this.z = source.getZ();
		this.width = source.getWidth();
		this.height = source.getHeight();
	}
	
	// Member constructor
	public UMLObject(String nLabel, int nX, int nY, int nZ, int nWidth, int nHeight, boolean nSelected) 
	{
		this.label = nLabel;
		this.x = nX;
		this.y = nY;
		this.z = nZ;
		this.width = nWidth;
		this.height = nHeight;
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
		
		serialString += "  <x>" + this.x + "</x>\n";
		serialString += "  <y>" + this.y + "</y>\n";
		serialString += "  <z>" + this.z + "</z>\n";
		serialString += "  <width>" + this.width + "</width>\n";
		serialString += "  <height>" + this.height + "</height>\n";
		
		return serialString;
	}
		
	// Returns object's label.
	public String getLabel()
	{
		return this.label;
	}


	public int getX() { return this.x; }
	
	public int getY() { return this.y; }
	
	public int getZ() { return this.z; }
	
	public int getWidth() { return this.width; }
	
	public int getHeight() { return this.height; }
	
	public boolean within(int x, int y) 
	{
		if (x >= this.x && x <= this.x + this.width &&
				y >= this.y && y <= this.y + this.height)
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
		return new UMLObject(newLabel, this.x, this.y, this.z, this.width, this.height, this.selected);
	}
	
	public UMLObject move(int x, int y, int z) 
		throws Exception
	{
		return new UMLObject(this.label, x, y, z, this.width, this.height, this.selected);
	}

	public UMLObject resize(int width, int height)
		throws Exception
	{
		return new UMLObject(this.label, this.x, this.y, this.z, width, height, this.selected);
	}

	//********************************************************************
	// Custom Drawing
	//********************************************************************
	
	public void draw(Graphics g)
	{
		if (this.selected)
		{
			g.setColor(Color.BLUE);
			g.fillRect(this.x - 5, this.y - 5, this.width + 10, this.height + 10);
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(this.x, this.y, this.width, this.height);
		g.setColor(Color.BLACK);
		g.drawRect(this.x, this.y, this.width, this.height);
		
		g.drawString(this.label, this.x+15, this.y+15);
	}

	@Override
	public Point getOrigin() 
	{
		return new Point(this.x, this.y);
	}

	@Override
	public Point getBound() {
		return new Point(this.x + this.width, this.y + this.height);
	}
	
	public int getType() { return DrawableUML.OBJECT; }

	public boolean getSelected() { return this.selected; }
	
	@Override
	public UMLObject select() {
		return new UMLObject(this.label, this.x, this.y, this.z, this.width, this.height, true);
	}

	@Override
	public UMLObject unselect() {
		return new UMLObject(this.label, this.x, this.y, this.z, this.width, this.height, false);
	}
}