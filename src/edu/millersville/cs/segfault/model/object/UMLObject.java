package edu.millersville.cs.segfault.model.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.XMLAttribute;

/*****************************************************************************
 * UMLObject is the top level representation of all non-relation objects
 * in UML diagrams.
 * 
 * @author Daniel Rabiega
 */

public class UMLObject implements DrawableUML {
	
	
	//************************************************************************
	// Instance Variables
	
	public final ImmutableLabel[] text;
	public final ImmutablePoint origin;
	public final int z;     
	public final Dimension size;
	public final boolean selected; 
	
	
	//************************************************************************
	// Constructors
	
	/*************************************************************************
	 * Creates a new, default, UMLObject
	 */
	public UMLObject(){
		this.text = new ImmutableLabel[this.getType().textQuantity];
		this.origin = new ImmutablePoint(0,0);
		this.z = 0;
		this.size = new Dimension(100,100);
		selected = false;
	
	}
	
	/*************************************************************************
	 * Recreates a UMLObject from a serialized representation.
	 */
	public UMLObject(String s) throws Exception {
		ArrayList<ImmutableLabel> newText = new ArrayList<ImmutableLabel>();
		String textSection = XMLAttribute.getAttribute(s, "text");
		
		int textSearch = 0;
		while(XMLAttribute.hasAttr(textSection, "" + textSearch)) {
			newText.add(new ImmutableLabel(XMLAttribute.getAttribute(textSection, "" + textSearch)));
		}
		
		this.text = newText.toArray(this.text);
		
		this.z = new Integer(XMLAttribute.getAttribute(s, "z"));
		this.origin = new ImmutablePoint(XMLAttribute.getAttribute(s, "origin"));
		this.size = new Dimension(XMLAttribute.getIntAttribute(s, "width"), 
								  XMLAttribute.getIntAttribute(s, "height"));
		this.selected = false;
	}
	
	/*************************************************************************
	 * Creates a new UMLObject from it's components.
	 */
	public UMLObject(ImmutableLabel[] text, ImmutablePoint origin, int nZ, Dimension size, boolean nSelected) 
	{
		if (size.width < 50) {
			size = new Dimension(50, size.height);
		}
		if (size.height < 50) {
			size = new Dimension(size.width, 50);
		}
		
		this.text = text;
		this.origin = origin;
		this.size = size;
		this.z = nZ;
		this.selected = nSelected;
	}
	
	//************************************************************************
	// Observers
		
	/*************************************************************************
	 * Creates a serialized representation of this object. 
	 */
	public String serialize()
	{
		return XMLAttribute.makeTag(this.getType().name(), this.toString());
	}
			
	/*************************************************************************
	 * Creates a serialized representation of the properties of this object.
	 */
	public String toString() {
		return XMLAttribute.makeTag("text", this.serializeText())
			 + XMLAttribute.makeTag("origin", this.origin.serialize())
			 + XMLAttribute.makeTag("width", this.size.width)
			 + XMLAttribute.makeTag("height", this.size.height)
			 + XMLAttribute.makeTag("z", this.z);
	}
	
	private String serializeText() {
		String textString = "";
		for (int i=0; i<this.text.length; ++i) {
			textString += XMLAttribute.makeTag("" + i, this.text[i].text);
		}
		return textString;
	}
	
	
	/*************************************************************************
	 * Returns the DrawableType of this object.
	 */
	public DrawableType getType() { return DrawableType.OBJECT; }
	
	/*************************************************************************
	 * Returns the z position of this object. 
	 */
	public int getZ() { return this.z; }

	
	@Override
	public boolean hit(ImmutablePoint point) {
		return point.getX() > this.origin.getX() &&
			   point.getX() < this.origin.getX() + this.size.getWidth() &&
			   point.getY() > this.origin.getY() && 
			   point.getY() < this.origin.getY() + this.size.getHeight();
		
	}
	
	/*************************************************************************
	 * Returns true if the given point is within this object.
	 */
	@Override
	public boolean isWithin(Rectangle2D dragArea) {
		Rectangle2D me = new Rectangle2D.Double(this.origin.x, this.origin.y, this.size.width, this.size.height);
		if (dragArea.contains(me)) {
			return true;
		}
		return false;
	}
	
	/*************************************************************************
	 * Returns the closest point on this shape to a given point.
	 */
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
	
	/*************************************************************************
	 * Returns true if the object is currently selected.
	 */
	public boolean isSelected() { return this.selected; }
	
	public ImmutablePoint getCorner() {
		return new ImmutablePoint(this.origin.x + this.size.width, this.origin.y + this.size.height);
	}
	
	public int nearestCorner(ImmutablePoint p) {
		ImmutablePoint[] corners = { this.origin, 
									 new ImmutablePoint(this.origin.x + this.size.width/2, this.origin.y),
									 new ImmutablePoint(this.origin.x + this.size.width, this.origin.y),
									 new ImmutablePoint(this.origin.x, this.origin.y + this.size.height/2),
									 new ImmutablePoint(this.origin.x + this.size.width, this.origin.y + this.size.height/2),
									 new ImmutablePoint(this.origin.x, this.origin.y + this.size.height),
									 new ImmutablePoint(this.origin.x + this.size.width/2, this.origin.y + this.size.height),
									 this.getCorner() };
		
		int min = 0;
		for (int i=1; i<corners.length; ++i) {
			if (p.distance(corners[i]) < p.distance(corners[min])) { min = i; }
		}
		return min;
	}
	
	public ImmutableLabel[] getText() {
		return this.text;
	}
	
	public ImmutableLabel getText(int n) {
		return this.text[n];
	}
	
	public ImmutablePoint textPos(int n) {
		if (n != 0) { return null; }
		return this.origin.translate(10, 10);
	}

	//********************************************************************
	// Mutators
	//********************************************************************

	/*************************************************************************
	 * Returns a new UMLObject with a different location.
	 */
	public UMLObject translate(int deltaX, int deltaY) 
	{
		UMLObject newObject = this;
		try {
			newObject = DrawableType.makeObject(this.text, this.getType(), 
												this.origin.translate(deltaX, deltaY), 
												this.size, z, this.selected);
		} catch (Exception e) {
			
		}
		return newObject;
	}

	/*************************************************************************
	 * Returns a new UMLObject with a different size.
	 */
	public UMLObject resize(int width, int height)
		throws Exception
	{
		return new UMLObject(this.text, this.origin, this.z, new Dimension(width, height), this.selected);
	}

	/*************************************************************************
	 * Returns a selected copy of this UMLObject.
	 */
	@Override
	public UMLObject select() {
		try {
			return DrawableType.makeObject(this.text, getType(), this.origin, this.size, this.z, true);
		} catch (Exception e) {
			return null;
		}
	}

	/*************************************************************************
	 * Returns a de-selected copy of this UMLObject.
	 */
	@Override
	public UMLObject unselect() {
		try {
			return DrawableType.makeObject(this.text, getType(), this.origin, this.size, this.z, false);
		} catch (Exception e) {
			return null;
		}
	}
	
	/*********************************************************************
	 * Returns a new version of this object with size adjusted by
	 * deltaX and deltaY
	 */
	public UMLObject scale(int deltaX, int deltaY) {
		UMLObject newObject = this;
		try {
			newObject = DrawableType.makeObject(this.text, this.getType(), this.origin, 
						  			            new Dimension(this.size.width + deltaX, this.size.height + deltaY),
									            deltaY, selected);
		} catch (Exception e) {
			
		}
		return newObject;
	}
	
	public UMLObject setText(ImmutableLabel[] newText) {
		UMLObject object = this;
		try {
			object = DrawableType.makeObject(newText, this.getType(), this.origin, 
											 this.size, this.z, this.selected);
		} catch (Exception e) {
			System.out.println("Could not change text!");
		}
		return object;
	}
	
	public UMLObject setText(ImmutableLabel label, int n) {
		UMLObject object = this;
		ImmutableLabel[] newText = new ImmutableLabel[this.text.length];
		try {
			for (int i=0; i<this.text.length; ++i) {
				newText[i] = this.text[i];
				if (i == n) { newText[i] = label; }
			}
			object = DrawableType.makeObject(newText, this.getType(), this.origin, 
											 this.size, this.z, this.selected);
		} catch (Exception e) {
			System.out.println("Could not change text!");
		}
		return object;
	}	 
	
	//********************************************************************
	// Custom Drawing
	//********************************************************************
	
	public void draw(Graphics g)
	{
		Dimension drawSize = getSize(g);
				
		g.setColor(Color.WHITE);
		g.fillRect(this.origin.x, this.origin.y, drawSize.width, drawSize.height);
		
		g.setColor(Color.BLACK);
		if (this.selected)
		{
			g.setColor(Color.BLUE);
		}
		g.drawRect(this.origin.x, this.origin.y, drawSize.width, drawSize.height);
		
		if (this.getType() != DrawableType.OBJECT) { return; }
		
		int xMargin = (drawSize.width  - this.text[0].getWidth(g))/2;
		int yMargin = (drawSize.height - this.text[0].getHeight(g))/2;
		
		this.text[0].draw(g, new ImmutablePoint(this.origin.x + xMargin, this.origin.y + yMargin));
		
	}
	
	public Dimension getSize(Graphics g) {
		return new Dimension(Math.max(this.size.width,  this.minimumWidth(g)), 
				   Math.max(this.size.height, this.minimumHeight(g)));
	}

	public int minimumHeight(Graphics g) {
		int accum = 0;
		for (int i=0; i<this.text.length; ++i) {
			accum += this.text[i].getHeight(g) + 20;
		}
		return accum;
	}
	
	public int minimumWidth(Graphics g) {
		int max = this.text[0].getWidth(g);
		for (int i=1; i<this.text.length; ++i) {
			int newSize = this.text[i].getWidth(g);
			if (max < newSize) { max = newSize; }
		}
		return max + 20;
	}
	
	/*************************************************************************
	 * Returns an ImmutablePath representing the edges of this object.
	 */
	public ImmutablePath getPath() {
		int x1 = this.origin.x;
		int x2 = x1 + this.size.width;
		int y1 = this.origin.y;
		int y2 = y1 + this.size.height;
		
		ImmutablePath newPath = new ImmutablePath(this.origin);
		newPath = newPath.addLast(new ImmutablePoint(x2, y1));
		newPath = newPath.addLast(new ImmutablePoint(x2, y2));
		newPath = newPath.addLast(new ImmutablePoint(x1, y2));
		newPath = newPath.addLast(this.origin);
		
		return newPath;
	}
}