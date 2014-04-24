package edu.millersville.cs.segfault.model.relation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import edu.millersville.cs.segfault.immutable.ImmutableLine;
import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;

/*****************************************************************************
 * A subclass of UMLRelation which is ended by a filled diamond.
 * @author Daniel Rabiega
 ****************************************************************************/
public class Composition extends UMLRelation {
	
	//************************************************************************
	// Static Members
	
	private static int[] xPoints = {0, 10, 0, -10, 0};
	private static int[] yPoints = {0, -20, -30, -20, 0};

	//************************************************************************
	// Constructors
	
	/*************************************************************************
	 * Creates a new Composition with the given properties.
	 *************************************************************************/

	public Composition(ImmutablePath path, int z, boolean selected) {
		super(path, z, selected);
	}
	
	/*************************************************************************
	 * Recreates a Composition from an XML representation
	 *************************************************************************/

	public Composition(String serialized) 
		throws Exception
	{
		super(serialized);
	}
	
	//************************************************************************
	// Observers
	
	/*************************************************************************
	 * Returns this composition's type, COMPOSITION
	 *************************************************************************/

	public DrawableType getType() { return DrawableType.COMPOSITION; }
	
	//************************************************************************
	// Mutators
	
	/*************************************************************************
	 * Returns a copy of this composition with a new line segment.
	 **************************************************************************/

	public Composition extend(ImmutablePoint p) {
		return new Composition(this.getPath().addLast(p), this.getZ(), this.isSelected());
	}
	
	//************************************************************************
	// Drawing Methods
	
	/*************************************************************************
	 * Draws an filled diamond at this aggregation's endpoint.
	 *************************************************************************/
	public void drawArrow(Graphics g) {
		if (this.getPath().size() >= 2) {
			Iterator<ImmutableLine> lIter = this.getPath().lineIterator();
			ImmutableLine lastLine = null;
			while (lIter.hasNext()) { lastLine = lIter.next(); }
			
			Graphics2D newG = (Graphics2D) g.create();
			newG.translate(lastLine.second.getX(), lastLine.second.getY());
			
			double theta = Math.atan2(lastLine.first.getY()-lastLine.second.getY(),
									  lastLine.first.getX()-lastLine.second.getX());
			
			theta = theta + Math.PI*.5;
			
			AffineTransform rotate = new AffineTransform();
			rotate.setToRotation(theta);
			newG.transform(rotate);
			
			newG.setColor(Color.BLACK);
			newG.fillPolygon(xPoints, yPoints, 5);
			
		}
	}
}
