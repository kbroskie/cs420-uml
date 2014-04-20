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
		super.draw(g);
		g.setColor(Color.black);
		int afterFirstText = this.origin.y + this.getText(0).getHeight(g) + 20;
		int afterSecondText = afterFirstText + this.getText(1).getHeight(g) + 20;
		this.getText(0).draw(g, this.origin.translate(10, 10));
		g.drawLine(this.origin.x, afterFirstText, this.origin.x + this.size.width, afterFirstText);
		this.getText(1).draw(g, this.origin.translate(10, afterFirstText + 10));
		g.drawLine(this.origin.x, afterSecondText, this.origin.x + this.size.width, afterSecondText);
		
	}

}

