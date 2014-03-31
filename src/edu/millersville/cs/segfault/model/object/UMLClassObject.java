/*
 * A subclass that extends the UML object to draw the class UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import edu.millersville.cs.segfault.immutable.ImmutableLine;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.XMLAttribute;

	public class UMLClassObject extends UMLObject {
		
//*********************************************************************
//Static variables
		
	public final ImmutableLine firstLine;
	public final ImmutableLine secondLine;
		
		
//*********************************************************************
//Constructors
		
	//Empty constructor
	public UMLClassObject()
	{
		super();
		this.firstLine  = new ImmutableLine(new ImmutablePoint(this.origin.x, this.size.height/3), 
						  new ImmutablePoint(this.origin.x + this.size.width, this.size.height/3));
		this.secondLine = new ImmutableLine(new ImmutablePoint(this.origin.x, (this.size.height/3)*2), 
				          new ImmutablePoint(this.origin.x + this.size.width, (this.size.height/3)*2));
	}

	//Member constructor.
	public UMLClassObject (String nLabel, ImmutablePoint p, int nZ, Dimension size, boolean nSelected)
			throws Exception
	{
		super(nLabel, p, nZ, size, nSelected);
		this.firstLine  = new ImmutableLine(new ImmutablePoint(this.origin.x, this.size.height/3), 
		        		  new ImmutablePoint(this.origin.x + this.size.width, this.size.height/3));
		this.secondLine = new ImmutableLine(new ImmutablePoint(this.origin.x, (this.size.height/3)*2), 
		          		  new ImmutablePoint(this.origin.x + this.size.width, (this.size.height/3)*2));
	}

	public UMLClassObject (String s) 
		throws Exception
	{
		super(s);
		this.firstLine = new ImmutableLine(XMLAttribute.getAttribute(s, "firstLine"));
		this.secondLine = new ImmutableLine(XMLAttribute.getAttribute(s, "secondLine"));
	}
		
//**********************************************************************
//Observers

	public String toString(String name) {
		return super.toString() + firstLine.serialize("firstLine") + secondLine.serialize("secondLine"); 
	}

	public DrawableType getType() { return DrawableType.CLASS; }
	
//**********************************************************************
//Mutators

	//Change Label
		

	//Move
	public UMLClassObject moveClass (ImmutablePoint p, int z) throws Exception
	{
		return new UMLClassObject (this.label, p, z, this.size, this.isSelected());
	}

	//Resize
	public UMLClassObject resizeClass (Dimension size) throws Exception
	{
		return new UMLClassObject (this.label, this.origin, this.getZ(), size, this.isSelected());
	}
	
//************************************************************************
//Drawing Method

	public void draw (Graphics g)
	{
		super.draw(g);
		g.setColor(Color.black);
		firstLine.draw(g);
		secondLine.draw(g);
	}

}

