package edu.millersville.cs.segfault.model.object;

import java.awt.*;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;

	public class UMLClassObject extends UMLObject {
		int line1Height;
		int line2Height;
		int line1Y;
		int line2Y;

//*********************************************************************
//Empty constructor
		public UMLClassObject()
		{
			super();
			line1Height = 0;
			line2Height = 0;
			line1Y = 0;
			line2Y = 0;
		}

//Copy constructor
		public UMLClassObject (UMLClassObject source)
		{
			super(source);
			this.line1Height = source.getL1Height();
			this.line2Height = source.getL2Height();
			this.line1Y = source.getLine1Y();
			this.line2Y = source.getLine2Y();
}

	//Member constructor.
		public UMLClassObject (String nLabel, ImmutablePoint p, int nZ, Dimension size, boolean nSelected)
				throws Exception
		{
			super(nLabel, p, nZ, size, nSelected);
			this.line1Height = size.height/2;
			this.line2Height = size.height/4;
			this.line1Y = p.getY() + line1Height;
			this.line2Y = p.getY() + line2Height;
		}

		public UMLClassObject (String serialized) 
			throws Exception
		{
			super(serialized);
		}
		
//**********************************************************************
//Observers

		public int getL1Height ()
		{
			return this.line1Height;
		}

		public int getL2Height ()
		{
			return this.line2Height;
		}

		public int getLine1Y ()
		{
			return this.line1Y;
		}

		public int getLine2Y ()
		{
			return this.line2Y;
		}

//**********************************************************************
//Mutators

//changelabel
		

//Move

	public UMLClassObject moveClass (ImmutablePoint p, int z) throws Exception
	{
		return new UMLClassObject (this.getLabel(), p, z, this.getSize(), this.isSelected());
	}

//Resize
	public UMLClassObject resizeClass (Dimension size) throws Exception
	{
		return new UMLClassObject (this.getLabel(), this.getOrigin(), this.getZ(), size, this.isSelected());
	}
	
// We are attempting to draw this thing.

	public void draw (Graphics g)
	{
		super.draw(g);
		g.setColor(Color.black);
		g.drawLine(this.getX(), line1Y, this.getX() + this.getWidth(), line1Y);
		g.drawLine(this.getX(), line2Y, this.getX() + this.getWidth(), line2Y);
	}

}

