/*
 * A subclass that extends UMLObject to draw the state UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;

public class State extends UMLObject {
	
//**********************************************************************************
//Constructors
	
	//Empty constructor
	public State() 
	{
		super();
	}
						
	//Member constructor
	public State (ImmutableLabel[] text, ImmutablePoint p, int nZ, Dimension size, boolean nSelected) 
				throws Exception
	{
		super(text, p, nZ, size, nSelected);
				
	}
	
	public State (String duper) 
		throws Exception
	{
		super(duper);
	}

	
	public DrawableType getType() { return DrawableType.STATE; }
//***********************************************************************************
//Drawing Method
	
	//Draw state object
	public void draw (Graphics g)
	{
		//Currently testing 1 as arcHeight and arcWidth
		g.setColor(Color.WHITE);
		g.fillRoundRect(this.origin.x, this.origin.y, this.size.width, this.size.height, 40, 40);
		g.setColor(Color.BLACK);
		g.drawRoundRect(this.origin.x, this.origin.y, this.size.width, this.size.height, 40, 40);
		
	}
}
