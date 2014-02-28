/**
 * @author Team Segfault
 * @version 1.0
 * @since 2014-02-20
 */

package edu.millersville.cs.segfault.model;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.ui.UMLPanel;


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
	ImmutablePoint snapPoint(ImmutablePoint point);
	boolean isSelected();
	boolean hit(ImmutablePoint point);
	boolean isWithin(Rectangle2D dragArea);
}
