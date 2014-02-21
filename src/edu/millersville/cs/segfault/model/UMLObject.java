/**
 * @author Team Segfault
 * @version 1.0
 * @since 2014-02-20
 */

package edu.millersville.cs.segfault.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class UMLObject implements DrawableUML {
	
	//********************************************************************
	// Object change types.
	public static final int SET_LABEL = 0; // Change the object's label.
	public static final int SET_COORDS = 1;
	public static final int SET_DIMENSION = 2;
	public static final int SET_ID = 3;
	//********************************************************************
	
	/**
	 * De-Serialization helpers
	 * @param attr
	 * @param serialized
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * Empty constructor
	 */
	public UMLObject(){
		this.label = "";
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.width = 100;
		this.height = 100;
		selected = false;
	
	}
	
	/**
	 * De-serialization constructor.
	 * @param serialized The string to be de-serialized.
	 * @throws Exception
	 */
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
	
	/**
	 * Copy constructor.
	 * @param source The UMLObject to be copied.
	 */
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
	
	/**
	 * Inject string constructor.
	 * @param source The UMLObject to be labeled.
	 * @param changeType The action to be done.
	 * @param newString The new label.
	 * @throws Exception
	 */
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

	/**
	 * Inject position constructor
	 * @param source The object to position.
	 * @param changeType The action to be done.
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param z Z-coordinate
	 * @throws Exception
	 */
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
	
	/**
	 * Inject dimension constructor
	 * @param source Object to set dimension.
	 * @param changeType Action that will be done.
	 * @param width  Width of object.
	 * @param height Height of object. 
	 * @throws Exception
	 */
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
	
	/**
	 * Returns serialized version of object.
	 * @return serialString The serialized string.
	 */
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
		
	/**
	 * Returns object's label.
	 * @return this.label The object's label.
	 */
	public String getLabel()
	{
		return this.label;
	}

	/**
	 * Gets the x-coordinate.
	 * @return this.x Object's x-coordinate.
	 */
	public int getX() { return this.x; }
	
	/**
	 * Gets the y-coordinate.
	 * @return this.y Object's y-coordinate.
	 */
	public int getY() { return this.y; }
	
	/**
	 * Gets the z-coordiante.
	 * @return this.z Object's z-coordinate.
	 */
	public int getZ() { return this.z; }
	
	/**
	 * Gets the object's width.
	 * @return this.width Returns the object's width.
	 */
	public int getWidth() { return this.width; }
	
	/**
	 * Gets the object's height.
	 * @return this.height Returns the object's height.
	 */
	public int getHeight() { return this.height; }
	
	/**
	 * Checks to see whether the object is within a certain dimensions
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @return boolean Whether or not the object is with the dimensions.
	 */
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

	/**
	 * Change the label.
	 * @param newLabel The new label.
	 * @return newObject The object with the new label.
	 * @throws Exception
	 */
	public UMLObject changeLabel(String newLabel)
		throws Exception
	{
		UMLObject newObject = new UMLObject(this, SET_LABEL, newLabel);
		
		return newObject;
	}
	
	/**
	 * Moves the object.
	 * @param x Target x-coordinate.
	 * @param y Target y-coordinate.
	 * @param z Target z-coordinate.
	 * @return
	 * @throws Exception
	 */
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

	@Override
	public void select() {
		this.selected = true;
	}

	@Override
	public void unselect() {
		this.selected = false;
	}
}