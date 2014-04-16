/*
 * A subclass that extends the class object to draw the active class UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.*;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;

public class ActiveClass extends ClassObject {

	//*************************************************************************
	//Constructors	
		
	//Empty constructor
	public ActiveClass () 
	{
		super();
	}
				
	//Member constructor
	public ActiveClass (String nLabel, ImmutablePoint p, int nZ, Dimension size, boolean nSelected) 
			throws Exception
	{
		super(nLabel, p, nZ, size, nSelected);
			
	}
	
	//Deserialization constructor
	public ActiveClass(String serial) 
		throws Exception
	{
		super(serial);
	}
	
	//*************************************************************************
	// Observers
	
	public DrawableType getType() { return DrawableType.ACTIVE_CLASS; }
	
	//*************************************************************************
	// Drawing Methods
	
	//Draw active class
	public void draw (Graphics g) 
	{
		//Draw outer black box
		g.setColor(Color.BLACK);
		g.fillRect(this.origin.x - 5, this.origin.y - 5, this.size.width + 10, this.size.height + 10);
			
		super.draw(g);
	}
}
