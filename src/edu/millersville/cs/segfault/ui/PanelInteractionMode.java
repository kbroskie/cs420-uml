package edu.millersville.cs.segfault.ui;

import java.awt.Graphics;

public interface PanelInteractionMode {
	void left_click(int x, int y);
	void draw(Graphics g, int mouseX, int mouseY);
	void right_click(int x, int y);
}
