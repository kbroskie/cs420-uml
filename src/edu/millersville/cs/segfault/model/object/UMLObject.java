package edu.millersville.cs.segfault.model.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

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
	

	public final String label;  
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
		this.label = "";
		this.origin = new ImmutablePoint(0,0);
		this.z = 0;
		this.size = new Dimension(100,100);
		selected = false;
	
	}
	
	/*************************************************************************
	 * Recreates a UMLObject from a serialized representation.
	 */
	public UMLObject(String s) throws Exception {
		this.label = XMLAttribute.getAttribute(s, "label");
		this.z = new Integer(XMLAttribute.getAttribute(s, "z"));
		this.origin = new ImmutablePoint(XMLAttribute.getAttribute(s, "origin"));
		this.size = new Dimension(XMLAttribute.getIntAttribute(s, "width"), 
								  XMLAttribute.getIntAttribute(s, "height"));
		this.selected = false;
	}
	
	/*************************************************************************
	 * Creates a new UMLObject from it's components.
	 */
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
		return XMLAttribute.makeTag("label", this.label) 
			 + XMLAttribute.makeTag("origin", this.origin.serialize())
			 + XMLAttribute.makeTag("width", this.size.width)
			 + XMLAttribute.makeTag("height", this.size.height)
			 + XMLAttribute.makeTag("z", this.z);
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
	
	//********************************************************************
	// Mutators
	//********************************************************************

	/*************************************************************************
	 * Returns a new UMLObject with a different label.
	 */
	public UMLObject changeLabel(String newLabel)
		throws Exception
	{
		return new UMLObject(newLabel, this.origin, this.z, this.size, this.selected);
	}
	
	/*************************************************************************
	 * Returns a new UMLObject with a different location.
	 */
	public UMLObject move(int x, int y, int z) 
		throws Exception
	{
		return new UMLObject(this.label, new ImmutablePoint(x, y), z, this.size, this.selected);
	}

	/*************************************************************************
	 * Returns a new UMLObject with a different size.
	 */
	public UMLObject resize(int width, int height)
		throws Exception
	{
		return new UMLObject(this.label, this.origin, this.z, new Dimension(width, height), this.selected);
	}

	/*************************************************************************
	 * Returns a selected copy of this UMLObject.
	 */
	@Override
	public UMLObject select() {
		return new UMLObject(this.label, this.origin, this.z, this.size, true);
	}

	/*************************************************************************
	 * Returns a de-selected copy of this UMLObject.
	 */
	@Override
	public UMLObject unselect() {
		return new UMLObject(this.label, this.origin, this.z, this.size, false);
	}
	
	//********************************************************************
	// Custom Drawing
	//********************************************************************
	
	public void draw(Graphics g)
	{
		
		g.setColor(Color.WHITE);
		g.fillRect(this.origin.x, this.origin.y, this.size.width, this.size.height);
		
		g.setColor(Color.BLACK);
		if (this.selected)
		{
			g.setColor(Color.BLUE);
		}
		g.drawRect(this.origin.x, this.origin.y, this.size.width, this.size.height);

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