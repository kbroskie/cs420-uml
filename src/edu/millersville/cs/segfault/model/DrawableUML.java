package edu.millersville.cs.segfault.model;

import java.awt.Graphics;
import java.awt.Point;

public interface DrawableUML {
	static final int OBJECT = 0;
	static final int RELATION = 1;
	
	int getZ();
	void draw(Graphics g);
	Point getOrigin();
	Point getBound();
	int getType();
	void select();
	void unselect();
}
