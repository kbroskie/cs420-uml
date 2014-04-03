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
		g.setColor(Color.white);
		g.fillRect(this.origin.x - this.size.width/9, this.origin.y + this.size.height/4, this.size.width/3, this.size.height/4);
		g.fillRect(this.origin.x - this.size.width/9, this.origin.y + (this.size.height *2)/3, this.size.width/3, this.size.height/4);
		g.setColor (Color.black);
		g.drawRect(this.origin.x - this.size.width/9, this.origin.y + this.size.height/4, this.size.width/3, this.size.height/4);
		g.drawRect(this.origin.x - this.size.width/9, this.origin.y + (this.size.height *2)/3, this.size.width/3, this.size.height/4);
		
	}
}
