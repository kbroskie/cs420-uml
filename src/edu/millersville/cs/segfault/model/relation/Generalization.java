package edu.millersville.cs.segfault.model.relation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import edu.millersville.cs.segfault.immutable.ImmutableLine;
import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.model.DrawableType;


/*****************************************************************************
 * A subclass of UMLRelation which is ended by an unfilled arrow.
 * @author Daniel Rabiega
 ****************************************************************************/
public class Generalization extends UMLRelation {

	private static int[] xPoints = {0,   10, -10,  0};
	private static int[] yPoints = {0,  -20, -20,  0};
	private static int[] newXs   = {0,    7,  -7,  0};
	private static int[] newYs   = {-4, -17, -17, -4};
	
	public Generalization(String serial) 
		throws Exception
	{
		super(serial);
	}
	
	public Generalization(ImmutablePath path, int z, boolean selected) {
		super(path, z, selected);
	}
	
	public DrawableType getType() { return DrawableType.GENERALIZATION; }
	
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
			newG.fillPolygon(xPoints, yPoints, 4);
			
				
			newG.setColor(Color.WHITE);
			newG.fillPolygon(newXs, newYs, 4);
		}
	}
}
