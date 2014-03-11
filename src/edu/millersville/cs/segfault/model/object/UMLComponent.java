package edu.millersville.cs.segfault.model.object;

import java.awt.*;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;

public class UMLComponent extends UMLObject 
{	
	//Empty constructor
	public UMLComponent ()
	{
		super();
	}
	
	//Copy constructor
	public UMLComponent (UMLComponent source)
	{
		super(source);
	}
	
	//Member constructor
	public UMLComponent (String nLabel, ImmutablePoint p, int nZ, Dimension size, boolean nSelected) 
	throws Exception
	{
		super(nLabel, p,nZ,size,nSelected);
		
	}
	
	/*****************************************************************************************/
	//Let's draw this
	
	public void draw (Graphics g)
	{
		super.draw(g);
		g.drawRect(this.getX() - 5, this.getY()/4, this.getWidth()/2, this.getHeight()/4);
		g.drawRect(this.getX() - 5, (this.getY()/4)*3, this.getWidth()/2, this.getHeight()/4);
		
	}
}
