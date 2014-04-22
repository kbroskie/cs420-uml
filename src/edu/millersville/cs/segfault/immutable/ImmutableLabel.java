package edu.millersville.cs.segfault.immutable;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/******************************************************************************
 * A non-swing immutable label object which can be told to draw at arbitrary
 * points.
 * 
 * @author Daniel Rabiega
 */
public class ImmutableLabel {
	
	//*************************************************************************
	// Instance Variables
	
	/**************************************************************************
	 * The text contained in this label.
	 */
	public final String text;	
	
	private final Font font;
	
	/**************************************************************************
	 * Whether or not this label is selected.
	 */
	public final boolean selected; 
	
	//*************************************************************************
	// Constructors
	
	/**************************************************************************
	 * Constructs a new, blank, immutable label.
	 */
	public ImmutableLabel() {
		this.text = "";
		this.font = new Font("Dialog", Font.PLAIN, 12);
		this.selected = false;
	}
	
	/**************************************************************************
	 * Constructs a new immutable label displaying the given text.
	 * @param text The text to display.
	 */
	public ImmutableLabel(String text) {
		this.text = text;
		this.font = new Font("Dialog", Font.PLAIN, 12);
		this.selected = false;
	}
	
	
	/**************************************************************************
	 * Constructs a new immutable label from a list of properties.
	 * @param text The text to display.
	 * @param font The font to display the text in.
	 * @param selected Whether or not the label should be selected.
	 */
	public ImmutableLabel(String text, Font font, boolean selected) {
		this.text = text;
		this.font = font;
		this.selected = selected;
	}
	
	//*************************************************************************
	// Observers

	/**************************************************************************
	 * Returns the font used to render the label.
	 * @return
	 */
	public Font getFont() { return this.font; }
	
	private FontMetrics metrics(Graphics g) { return g.getFontMetrics(this.font); }
	
	/**************************************************************************
	 * Returns the height required to display this label's text.
	 * @param g The graphics context in which to calculate display height.
	 * @return The height the label requires.
	 */
	public int getHeight(Graphics g) {
		return lineHeight(g) * lines().length;
	}
	
	private int lineHeight(Graphics g) {
		return (int) Math.round(metrics(g).getHeight()*.6);
	}
	
	/**************************************************************************
	 * Returns the width required to display this label's text.
	 * @param g The graphics context in which to calculate display width.
	 * @return The width the label requires.
	 */
	public int getWidth(Graphics g) {
		String[] lines = lines();
		
		int max = metrics(g).stringWidth(lines[0]);
		for (int i=0; i<lines.length; ++i) { 
			if (max < metrics(g).stringWidth(lines[i])) { max = metrics(g).stringWidth(lines[i]); }
		}
		return max;
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
	
	//*************************************************************************
	// Mutators
	
	
	/**************************************************************************
	 * Creates a new version of this label with a different font.
	 * @param newFont The font to be used with the new version.
	 * @return A new ImmutableLabel
	 */
	public ImmutableLabel setFont(Font newFont) {
		return new ImmutableLabel(this.text, newFont, this.selected);
	}
	
	/**************************************************************************
	 * Creates a new version of this label with modified text.
	 * @param newText The text which the new label is to contain.
	 * @return The modified label.
	 */
	public ImmutableLabel setText(String newText) {
		return new ImmutableLabel(newText, this.font, this.selected);
	}

	public ImmutableLabel select()   { return new ImmutableLabel(this.text, this.font, true); }
	
	public ImmutableLabel deselect() { return new ImmutableLabel(this.text, this.font, false); }
	
	//*************************************************************************
	// Drawing Methods
	
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
}
