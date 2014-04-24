package edu.millersville.cs.segfault.model.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;

/*****************************************************************************
 * An object with no display of it's own, only shows text within itself.
 */
public class FreeText extends UMLObject {
	
	public FreeText() {
		super();
	}
	
	public FreeText(String s) 
		throws Exception
	{
		super(s);
	}
	
	public FreeText(ImmutableLabel[] label, ImmutablePoint origin, int z, Dimension size, boolean selected) {
		super(label, origin, z, size, selected);
	}
	
	public DrawableType getType() {
		return DrawableType.FREE_TEXT;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		this.drawText(g);
	}
	
}
