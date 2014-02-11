package edu.millersville.cs.segfault.model;

import java.awt.Color;
import java.awt.Graphics;

public class UMLObject implements DrawableUML {
	
	//********************************************************************
	// Object change types.
	public static final int SET_LABEL = 0; // Change the object's label.
	public static final int SET_COORDS = 1;
	public static final int SET_DIMENSION = 2;
	public static final int SET_ID = 3;
	//********************************************************************
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
	
	}
	
	// De-serialization constructor
	public UMLObject(String serialized) throws Exception {
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
	
	// Inject string constructor
	public UMLObject(UMLObject source, int changeType, String newString)
		throws Exception
	{
		this(source);
		
		if (changeType == SET_LABEL) 
		{
			this.label = newString;
		} else {
			throw new Exception("Unrecognized string change type.");
		}
	}

	// Inject position constructor
	public UMLObject(UMLObject source, int changeType, int x, int y, int z)
		throws Exception
	{
		this(source);
		
		if (changeType == SET_COORDS) {
			this.x = x;
			this.y = y;
			this.z = z;
		} else {
			throw new Exception("Unrecognized coord change type.");
		}
	}
	
	// Inject dimension constructor
	public UMLObject(UMLObject source, int changeType, int width, int height)
		throws Exception
	{
		this(source);
		
		if (changeType == SET_DIMENSION) {
			this.width = width;
			this.height = height;
		} else {
			throw new Exception("Unrecognized dimension change type.");
		}
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
	
	//********************************************************************
	// Mutators
	//********************************************************************

	// Change the label.
	public UMLObject changeLabel(String newLabel)
		throws Exception
	{
		UMLObject newObject = new UMLObject(this, SET_LABEL, newLabel);
		
		return newObject;
	}
	
	public UMLObject move(int x, int y, int z) 
		throws Exception
	{
		return new UMLObject(this, SET_COORDS, x, y, z);
	}

	public UMLObject resize(int width, int height)
		throws Exception
	{
		return new UMLObject(this, SET_DIMENSION, width, height);
	}

	//********************************************************************
	// Custom Drawing
	//********************************************************************
	
	public void draw(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(this.x, this.y, this.width, this.height);
		g.setColor(Color.BLACK);
		g.drawRect(this.x, this.y, this.width, this.height);
	}
	
}