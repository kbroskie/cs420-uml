/*
 * A subclass that extends the class object to draw the active class UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.*;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;

public class UMLActiveClass extends UMLClassObject {

//***************************************************************************
//Constructors	
		
	//Empty constructor
	public UMLActiveClass () 
	{
		super();
	}
		
	//Copy constructor
	public UMLActiveClass (UMLActiveClass source)
	{
		super(source);
	}
		
	//Member constructor
	public UMLActiveClass (String nLabel, ImmutablePoint p, int nZ, Dimension size, boolean nSelected) 
			throws Exception
	{
		super(nLabel, p, nZ, size, nSelected);
			
	}
	
//*****************************************************************************
//Drawing Method
	
	//Draw active class
	public void draw (Graphics g) 
	{
		//Draw outer blackbox
		g.setColor(Color.BLACK);
		g.fillRect(this.getX() - 5, this.getY() - 5, this.getWidth() + 5, this.getHeight() + 5);
			
		super.draw(g);
	}
}
