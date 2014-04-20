/*
 * A subclass that extends UMLObject to draw the package UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;

public class Package extends UMLObject {
	
	//*************************************************************************
	//Constructors	
			
	//Empty constructor
	public Package () 
	{
		super();
	}
					
	//Member constructor
	public Package (ImmutableLabel[] text, ImmutablePoint p, int nZ, Dimension size, boolean nSelected) 
				throws Exception
	{
		super(text, p, nZ, size, nSelected);
				
	}
		
	//Deserialization constructor
	public Package(String serial) 
			throws Exception
	{
		super(serial);
	}

	//*************************************************************************
	//Observers
		
	public DrawableType getType() { return DrawableType.PACKAGE; }
		
	//*************************************************************************
	//Drawing method
	
	//FIX HEIGHT SO THAT Y2 IS THIS.ORIGIN.Y
	public void draw (Graphics g)
	{
		super.draw(g);
		g.setColor(Color.white);
		g.fillRect(this.origin.x, this.origin.y - this.size.height/8, this.size.width/3, this.size.height/8);
		g.setColor(Color.black);
		g.drawRect(this.origin.x, this.origin.y - this.size.height/8, this.size.width/3, this.size.height/8);
	}
}
