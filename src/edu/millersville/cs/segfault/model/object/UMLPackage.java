/*
 * A subclass that extends UMLObject to draw the package UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.*;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.object.UMLObject;

public class UMLPackage extends UMLObject {
	
	//*************************************************************************
	//Constructors	
			
	//Empty constructor
	public UMLPackage () 
	{
		super();
	}
					
	//Member constructor
	public UMLPackage (String nLabel, ImmutablePoint p, int nZ, Dimension size, boolean nSelected) 
				throws Exception
	{
		super(nLabel, p, nZ, size, nSelected);
				
	}
		
	//Deserialization constructor
	public UMLPackage(String serial) 
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
		g.fillRect(this.origin.x, this.origin.y - 20, this.size.width/3, this.size.height/8);
		g.setColor(Color.black);
		g.drawRect(this.origin.x, this.origin.y - 20, this.size.width/3, this.size.height/8);
	}
}
