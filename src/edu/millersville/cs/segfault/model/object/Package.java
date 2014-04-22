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
		int labelWidth = this.text[0].getWidth(g) + 20;
		int labelHeight = this.text[0].getHeight(g) + 10;
		
		int bodyWidth = Math.max(this.text[1].getWidth(g) + 20, this.size.width);
		int bodyHeight = Math.max(this.text[1].getHeight(g) + 10, this.size.height-labelHeight);
	
		g.setColor(Color.WHITE);
		g.fillRect(this.origin.x, this.origin.y, labelWidth, labelHeight);
		g.setColor(Color.BLACK);
		g.drawRect(this.origin.x, this.origin.y, labelWidth, labelHeight);
		this.text[0].draw(g, this.origin.translate(10,5));
		
		g.setColor(Color.WHITE);
		g.fillRect(this.origin.x, this.origin.y + labelHeight, bodyWidth, bodyHeight);
		g.setColor(Color.BLACK);
		g.drawRect(this.origin.x, this.origin.y + labelHeight, bodyWidth, bodyHeight);
		this.text[1].draw(g, this.origin.translate(10, labelHeight + 5));
		
	}
}
