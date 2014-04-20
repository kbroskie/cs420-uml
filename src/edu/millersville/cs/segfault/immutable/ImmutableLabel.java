package edu.millersville.cs.segfault.immutable;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class ImmutableLabel {
	
	public final String text;	
	
	private final Font font;
	
	public final boolean selected; 
	
	public ImmutableLabel() {
		this.text = "";
		this.font = new Font("Dialog", Font.PLAIN, 12);
		this.selected = false;
	}
	
	public ImmutableLabel(String text) {
		this.text = text;
		this.font = new Font("Dialog", Font.PLAIN, 12);
		this.selected = false;
	}
	
	public ImmutableLabel(String text, Font font, boolean selected) {
		this.text = text;
		this.font = font;
		this.selected = selected;
	}
	
	public ImmutableLabel setFont(Font newFont) {
		return new ImmutableLabel(this.text, newFont, this.selected);
	}
	
	public ImmutableLabel setText(String newText) {
		return new ImmutableLabel(newText, this.font, this.selected);
	}
	
	public Font getFont() { return this.font; }
	
	public ImmutableLabel select()   { return new ImmutableLabel(this.text, this.font, true); }
	public ImmutableLabel deselect() { return new ImmutableLabel(this.text, this.font, false); }
	
	public int getHeight(Graphics g) {
		FontMetrics metrics = g.getFontMetrics(this.font);
		return metrics.getHeight() * newLines();
	}
	
	public int lineHeight(Graphics g) {
		FontMetrics metrics = g.getFontMetrics(this.font);
		return metrics.getHeight();
	}
	
	
	public int getWidth(Graphics g) {
		FontMetrics metrics = g.getFontMetrics(this.font);
		
		String[] lines = lines();
		
		int max = metrics.stringWidth(lines[0]);
		for (int i=0; i<lines.length; ++i) { 
			if (max < metrics.stringWidth(lines[i])) { max = metrics.stringWidth(lines[i]); }
		}
		
		return max;
	}
	
	public void draw(Graphics g, ImmutablePoint p) {
		
		String[] lines = lines();
		for (int line = 0; line < lines.length; ++line) {
			g.drawString(lines[line], p.x, p.y+(this.lineHeight(g)*(line+1)));
		}
		
		if (this.selected) {
			g.setColor(Color.BLUE);
			g.drawRect(p.x-2, p.y-2, this.getWidth(g)+4, this.getHeight(g)+4);
		}
	}
	
	
	private int newLines() {
		return lines().length;
	}
	
	private String[] lines() {
		if (this.text.length()==0) { 
			String[] lines = new String[1];
			lines[0] = this.text;
			return lines;
		}
		String[] lines = this.text.split("\n");
		if (this.text.charAt(this.text.length()-1)=='\n') {
			String[] newLines = new String[lines.length + 1];
			for (int i=0; i < lines.length; ++i) { newLines[i] = lines[i]; }
			newLines[newLines.length-1] = "";
			lines = newLines;
		}
		return lines;
	}

}
