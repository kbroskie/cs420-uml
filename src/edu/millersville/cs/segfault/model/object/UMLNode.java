/*
 * A subclass that extends UMLObject to draw the Node UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.*;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.object.UMLObject;

public class UMLNode extends UMLObject {

//************************************************************************
//Constructors
	
	//Empty constructor
	public UMLNode ()
	{
		super();
	}
		
	//Member constructor
	public UMLNode (String nLabel, ImmutablePoint p, int nZ, Dimension size, boolean nSelected)
		throws Exception
	{
		super(nLabel, p, nZ, size, nSelected);
	}
	
	public UMLNode(String s) 
		throws Exception
	{
		super(s);
	}
	
	public DrawableType getType() { return DrawableType.NODE; }
	
//***************************************************************************
//Drawing Method
	
	public void draw (Graphics g)
	{
		g.setColor(Color.black);
		//g.draw3DRect(25, 110, 50, 75,true);
		g.draw3DRect(this.origin.x, this.origin.y, this.size.width, this.size.height, true);
	}

}

