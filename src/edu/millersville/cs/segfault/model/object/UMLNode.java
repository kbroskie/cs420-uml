/*
 * A subclass that extends UMLObject to draw the Node UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.*;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.object.UMLObject;

public class UMLNode extends UMLObject {
	
//************************************************************************
//Static Variables
	
	boolean raised = true;

//************************************************************************
//Constructors
	
	//Empty constructor
	public UMLNode ()
	{
		super();
	}
	
	//Copy Constructor
	public UMLNode (UMLNode source)
	{
		super(source);
	}
	
	//Member constructor
	public UMLNode (String nLabel, ImmutablePoint p, int nZ, Dimension size, boolean nSelected)
		throws Exception
	{
		super(nLabel, p, nZ, size, nSelected);
	}
	
//***************************************************************************
//Drawing Method
	
	public void draw (Graphics g)
	{
		g.draw3DRect(this.getX(), this.getY(), this.getWidth(), this.getHeight(), raised);
	}

}

