/*
 * A subclass that extends UMLUseCase to draw the Use Case UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */



package edu.millersville.cs.segfault.model.object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;

public class Collaboration extends UseCase {
	
	//*****************************************************************
		//Constructors
		
		//Empty constructor
		public Collaboration ()
		{
			super();
		}
		
		//Member constructor
		public Collaboration (ImmutableLabel[] text, ImmutablePoint p, int nZ, Dimension size, boolean nSelected)
			throws Exception
		{
			
			super(text, p, nZ, size, nSelected);
			
		}
		
		//Deserialization constructor
		public Collaboration (String serial) 
			throws Exception
		{
			super(serial);
		}
		
		//*******************************************************************
		//Observers
		
		public DrawableType getType() { return DrawableType.COLLABORATION; }
		
		//********************************************************************
		//Drawing Methods
		
		//Draw collaboration
		public void draw (Graphics g)
		{
			Graphics2D g2 = (Graphics2D) g;
			Stroke tempStroke = g2.getStroke();
			float dash[] = {10.0f};
			BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 
											     10.0f, dash, 0.0f);
			
			g2.setColor(Color.white);
			g2.fillOval(this.origin.x, this.origin.y, this.size.width, this.size.height);
			g2.setColor (Color.black);
			g2.setStroke(dashed);
			g2.draw(new Ellipse2D.Double(this.origin.x, this.origin.y, this.size.width, this.size.height));
			g2.setStroke(tempStroke);
			
			this.drawText(g);
		}
}
