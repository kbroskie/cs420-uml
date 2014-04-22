/*
 * A subclass that extends the UML object to draw the class UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutableLine;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.XMLAttribute;

	public class ClassObject extends UMLObject {
		
//*********************************************************************
//Static variables
				
		
//*********************************************************************
//Constructors
		
	//Empty constructor
	public ClassObject()
	{
		super();
	}

	//Member constructor.
	public ClassObject (ImmutableLabel[] text, ImmutablePoint p, int nZ, Dimension size, boolean nSelected)
			throws Exception
	{
		super(text, p, nZ, size, nSelected);
	}

	public ClassObject (String s) 
		throws Exception
	{
		super(s);
	}
	

	
//**********************************************************************
//Observers

	public String toString(String name) {
		return super.toString();
	}

	public DrawableType getType() { return DrawableType.CLASS; }
	
//**********************************************************************
//Mutators

	
//************************************************************************
//Drawing Method

	public void draw (Graphics g)
	{
		int classNameY = this.origin.y;
		int firstLine  = classNameY + 10 + this.text[0].getHeight(g);
		int membersY   = firstLine + 10;
		int secondLine = membersY + this.text[1].getHeight(g) + 10;
		int methodsY   = secondLine + 10;
		
		int width =  Math.max(this.size.width,  this.minimumWidth(g));
		int height = Math.max(this.size.height, this.minimumHeight(g));
		
		g.setColor(Color.WHITE);
		g.fillRect(this.origin.x, this.origin.y, width, height);
		
		g.setColor(Color.BLACK);
		g.drawRect(this.origin.x, this.origin.y, width, height);
		
		g.drawLine(this.origin.x, firstLine, this.origin.x + width, firstLine);
		g.drawLine(this.origin.x, secondLine, this.origin.x + width, secondLine);
		
		this.text[0].draw(g, new ImmutablePoint(this.origin.x+5, this.origin.y+5));
		this.text[1].draw(g, new ImmutablePoint(this.origin.x+5, membersY));
		this.text[2].draw(g, new ImmutablePoint(this.origin.x+5, methodsY));
	}

}

