	public class UMLClass extends UMLObject {
		int line1Height;
		int line2Height;
		int line1Y;
		int line2Y;
		
		//*********************************************************************
		//Empty constructor
		public UMLClass ()
		{
		super();
		line1Height = 0;
		line2Height = 0;
		line1Y = 0;
		line2Y = 0;
	
		}

		//Copy constructor
		public UMLClass (UMLClass source)
		{
			super(source);
		}

		//Member constructor.
		public UMLClass (String nLabel, int nX, int nY, int nZ, int nWidth, int nHeight, boolean nSelected) 
				throws Exception 
				{
					super(nLabel, nX, nY, nZ, nWidth, nHeight, nSelected);
					this.line1Height = height/2;
					this.line2Height = height/4;
					this.line1Y = y + line1Height;
					this.line2Y = y + line2Height;
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
		
		public UMLClass moveClass (int x, int y, int z)
		{
			UMLClass newClass (source); //"this" 
			super.move(x, y, z);
		}
		
		//Resize
		public UMLClass resizeClass (int width, int height)
		{
			UMLClass newClass (source);
			super.resize(width, height);
			line1Height = getLine1Height(); //?
		}
		
	
		
		
		// We are attempting to draw this thing.
		
		public void drawClass (Graphics g) 
		{
			super.draw(g);
			g.setColor(Color.black);
			g.drawLine(x, line1Y, x + width, line1Y);
		}

	}
}
