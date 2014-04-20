/**
 * @author Team Segfault
 * @version 1.0
 * @since 2014-02-20
 */

package edu.millersville.cs.segfault.model;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.ui.UMLPanel;


/*****************************************************************************
 * An interface for objects which can be inserted into a {@link UMLModel}    
 * and drawn on a {@link UMLPanel}                                           
 * @author Daniel Rabiega                                                    
 *****************************************************************************/
public interface DrawableUML {
	boolean isSelected();
	int getZ();
	void draw(Graphics g);
	DrawableType getType();
	DrawableUML select();
	DrawableUML unselect();
	ImmutablePoint snapPoint(ImmutablePoint point);
	boolean hit(ImmutablePoint point);
	boolean isWithin(Rectangle2D dragArea);
	ImmutablePath getPath();
	DrawableUML translate(int deltaX, int deltaY);
	ImmutableLabel[] getText();
	ImmutableLabel getText(int n);
	DrawableUML setText(ImmutableLabel label, int n);
	DrawableUML setText(ImmutableLabel[] label);
	ImmutablePoint textPos(int n);
}
