/**
 * @author Team Segfault
 * @version 1.0
 * @since 2014-02-20
 */

package edu.millersville.cs.segfault.model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;


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
	Point snapPoint(Point point);
	boolean isSelected();
	boolean hit(Point point);
	boolean isWithin(Rectangle2D dragArea);
}
