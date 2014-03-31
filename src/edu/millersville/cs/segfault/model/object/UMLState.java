/*
 * A subclass that extends UMLObject to draw the state UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.*;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.object.UMLObject;

public class UMLState extends UMLObject {
	
//**********************************************************************************
//Constructors
	
	//Empty constructor
	public UMLState() 
	{
		super();
	}
						
	//Member constructor
	public UMLState (String nLabel, ImmutablePoint p, int nZ, Dimension size, boolean nSelected) 
				throws Exception
	{
		super(nLabel, p, nZ, size, nSelected);
				
	}
	
	public UMLState (String duper) 
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
		g.setColor(Color.BLACK);
		g.drawRoundRect(this.origin.x, this.origin.y, this.size.width, this.size.height, 1, 1);
		
	}
}
