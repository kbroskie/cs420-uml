package edu.millersville.cs.segfault.model.relation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import edu.millersville.cs.segfault.immutable.ImmutableLine;
import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;

public class Aggregation extends UMLRelation {

	public Aggregation(ImmutablePath path, int z, boolean selected) {
		super(path, z, selected);
	}
	
	public Aggregation(String serialized) 
		throws Exception
	{
		super(serialized);
	}
	
	public void drawArrow(Graphics g) {
		if (this.getPath().size() >= 2) {
			Iterator<ImmutableLine> lIter = this.getPath().lineIterator();
			ImmutableLine lastLine = null;
			while (lIter.hasNext()) { lastLine = lIter.next(); }
			
			int[] xPoints = {0, 10, 0, -10, 0};
			int[] yPoints = {0, -20, -30, -20, 0};
			Graphics2D newG = (Graphics2D) g.create();
			newG.translate(lastLine.getSecond().getX(), lastLine.getSecond().getY());
			
			double theta = Math.atan2(lastLine.getFirst().getY()-lastLine.getSecond().getY(),
									  lastLine.getFirst().getX()-lastLine.getSecond().getX());
			
			theta = theta + Math.PI*.5;
			
			AffineTransform rotate = new AffineTransform();
			rotate.setToRotation(theta);
			newG.transform(rotate);
			
			newG.setColor(Color.BLACK);
			newG.fillPolygon(xPoints, yPoints, 5);
			
		}
	}
	
	public Aggregation extend(ImmutablePoint p) {
		return new Aggregation(this.getPath().addLast(p), this.getZ(), this.isSelected());
	}
}