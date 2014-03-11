package edu.millersville.cs.segfault.immutable;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;

public class ImmutableLabel {
	
	public final String text;
	public final Font font;
	public final boolean highlighted;
	
	
	public ImmutableLabel() {
		this.text = "";
		this.font = new Font("SansSerif", Font.PLAIN, 15);
		this.highlighted = false;
	}
	
	public ImmutableLabel(String text, Font font, boolean highlighted) {
		this.text = text;
		this.font = font;
		this.highlighted = highlighted;
	}
	
	public void draw(Graphics g, ImmutablePoint p) {
		g.drawString(text, p.getX() + 5, 
				(int) Math.round(p.getY() + this.font.getLineMetrics(text, new FontRenderContext(null, false, false)).getAscent()) + 2);
	}
	
	public int getHeight() {
		return this.font.getSize();
	}
	
	public int getWidth() {
		return (int) this.font.getStringBounds(text, new FontRenderContext(null, false, false)).getWidth() + 10;
	}
	
	public ImmutableLabel alterText(String text) {
		return new ImmutableLabel(text, this.font, this.highlighted);
	}
		
}
