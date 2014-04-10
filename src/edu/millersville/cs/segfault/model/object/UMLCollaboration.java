/*
 * A subclass that extends UMLUseCase to draw the Use Case UML
 * object.
 * @author Lindsay Blank (Team Seg Fault) lindsayrblank@gmail.com
 */



package edu.millersville.cs.segfault.model.object;

import java.awt.*;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;

public class UMLCollaboration extends UMLUseCase {
	
	//*****************************************************************
		//Constructors
		
		//Empty constructor
		public UMLCollaboration ()
		{
			super();
		}
		
		//Member constructor
		public UMLCollaboration (String nLabel, ImmutablePoint p, int nZ, Dimension size, boolean nSelected)
			throws Exception
		{
			
			super(nLabel, p, nZ, size, nSelected);
			
		}
		
		//Deserialization constructor
		public UMLCollaboration (String serial) 
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
			//g.setStroke(dashed);
			
		}
}
