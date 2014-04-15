/*
 * A subclass that extends UMLObject to draw the Node UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */

package edu.millersville.cs.segfault.model.object;

import java.awt.*;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.object.UMLObject;

public class UMLNode extends UMLObject {

//************************************************************************
//Constructors
	
	//Empty constructor
	public UMLNode ()
	{
		super();
	}
		
	//Member constructor
	public UMLNode (String nLabel, ImmutablePoint p, int nZ, Dimension size, boolean nSelected)
		throws Exception
	{
		super(nLabel, p, nZ, size, nSelected);
	}
	
	public UMLNode(String s) 
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

	}

}

