package edu.millersville.cs.segfault.model;

import java.awt.Graphics;
import java.awt.Point;

/*****************************************************************************
 * An interface for objects which can be inserted into a {@link UMLModel}    *
 * and drawn on a {@link UMLPanel}                                           *
 * @author Daniel Rabiega                                                    *
 *****************************************************************************/
public interface DrawableUML {
	int getZ();
	void draw(Graphics g);
	DrawableType getType();
	DrawableUML select();
	DrawableUML unselect();
	Point snapPoint(int x, int y);
}
