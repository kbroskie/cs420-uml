/*
 * A subclass that extends UMLObject to draw the Node UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;

public class Node extends UMLObject {

//************************************************************************
//Constructors
	
	//Empty constructor
	public Node ()
	{
		super();
	}
		
	//Member constructor
	public Node (ImmutableLabel[] text, ImmutablePoint p, int nZ, Dimension size, boolean nSelected)
		throws Exception
	{
		super(text, p, nZ, size, nSelected);
	}
	
	public Node(String s) 
		throws Exception
	{
		super(s);
	}
	
	public DrawableType getType() { return DrawableType.NODE; }
	
//***************************************************************************
//Drawing Method
	
	public void draw (Graphics g)
	{
		 Graphics2D g2 = (Graphics2D)g;
		 g.setColor(Color.WHITE);
         g.fillRect(this.origin.x, this.origin.y, this.size.width, this.size.height);
         g.setColor(Color.black);
         g.drawRect(this.origin.x, this.origin.y, this.size.width, this.size.height);
         Polygon p = new Polygon();
         int farLeftBack = (this.origin.x + (this.size.width / 3));
         int farRightBack = (this.size.width + farLeftBack);
         int farTopBack = (this.origin.y - (this.size.height / 3));
         int farBottomBack = (farTopBack + this.size.height);


         p.addPoint(this.origin.x + this.size.width, this.origin.y);
         p.addPoint(farRightBack, farTopBack);
         p.addPoint(this.origin.x + this.size.width,  this.origin.y);
         p.addPoint(this.origin.x, this.origin.y);
         p.addPoint(farLeftBack, farTopBack);
         p.addPoint(farRightBack, farTopBack);
         p.addPoint(farRightBack, farBottomBack);
         p.addPoint(this.origin.x + this.size.width, this.origin.y + this.size.height);

         g2.setColor(Color.WHITE);
         g2.fillPolygon(p);

         g2.setColor(Color.BLACK);
         g2.draw(p);
         this.drawText(g2);
	}

}

