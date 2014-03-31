/*
 * A subclass that extends UML object to draw the class UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.*;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;

public class UMLComponent extends UMLObject 
{	
	
//***************************************************************
//Constructors
	
	//Empty constructor
	public UMLComponent ()
	{
		super();
	}
		
	//Member constructor
	public UMLComponent (String nLabel, ImmutablePoint p, int nZ, Dimension size, boolean nSelected) 
	throws Exception
	{
		super(nLabel, p,nZ,size,nSelected);
		
	}
	
	public UMLComponent (String serious) 
		throws Exception
	{
		super(serious);
	}

	//*************************************************************************
	// Observer
	
	public DrawableType getType() { return DrawableType.COMPONENT; }
	
//*****************************************************************************************/
//Drawing Method
	
	public void draw (Graphics g)
	{
		super.draw(g);
		g.drawRect(this.origin.x - 5, this.origin.y/4, this.size.width/2, this.size.height/4);
		g.drawRect(this.origin.x - 5, (this.origin.y/4)*3, this.size.width/2, this.size.height/4);
		
	}
}
