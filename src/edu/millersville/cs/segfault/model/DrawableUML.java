package edu.millersville.cs.segfault.model;

import java.awt.Graphics;
import java.awt.Point;

public interface DrawableUML {
	int getZ();
	void draw(Graphics g);
	DrawableType getType();
	void select();
	void unselect();
	Point snapPoint(int x, int y);
}
