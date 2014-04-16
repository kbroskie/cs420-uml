/*
 * A subclass that extends UMLObject to draw the Use Case UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.*;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;

public class UseCase extends UMLObject {

	//*****************************************************************
	//Constructors
	
	//Empty constructor
	public UseCase ()
	{
		super();
	}
	
	//Member constructor
	public UseCase (String nLabel, ImmutablePoint p, int nZ, Dimension size, boolean nSelected)
		throws Exception
	{
		
		super(nLabel, p, nZ, size, nSelected);
		
	}
	
	//Deserialization constructor
	public UseCase (String serial) 
		throws Exception
	{
		super(serial);
	}
	
	//*******************************************************************
	//Observers
	
	public DrawableType getType() { return DrawableType.USE_CASE; }
	
	//********************************************************************
	//Drawing Methods
	
	//Draw use case
	
	public void draw (Graphics g)
	
	{
		g.setColor(Color.white);
		g.fillOval(this.origin.x, this.origin.y, this.size.width, this.size.height);
		g.setColor (Color.black);
		g.drawOval(this.origin.x, this.origin.y, this.size.width, this.size.height);

	}
	
}
