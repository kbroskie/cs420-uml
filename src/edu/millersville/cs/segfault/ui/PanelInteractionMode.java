package edu.millersville.cs.segfault.ui;

import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface PanelInteractionMode extends MouseListener, MouseMotionListener {
	void draw(Graphics g);
}
