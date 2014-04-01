package edu.millersville.cs.segfault.model.relation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import edu.millersville.cs.segfault.immutable.ImmutableLine;
import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.model.DrawableType;

public class Association extends UMLRelation {

	
	

	public Association(ImmutablePath path, int z, boolean selected)
	{
		super(path, z, selected);
	}
	
	public Association(String serialized)
		throws Exception
	{
		super(serialized);
	}
	
	public DrawableType getType() { return DrawableType.ASSOCIATION; }
	
	public void drawArrow(Graphics g) {
		if (this.getPath().size() > 1) {
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
			newG.drawLine(0, 0, 15, -15);
			newG.drawLine(0, 0, -15, -15);
		}
	}
	
	
	
}