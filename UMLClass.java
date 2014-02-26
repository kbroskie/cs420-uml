
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

		//Dimension constructor.
		public UMLClass (UMLObject source, int changeType, int width, int height) 
				throws Exception 
				{
					super(source, changeType, width, height);
					this.line1Height = height/2;
					this.line2Height = height/4;
				}

		//Position constructor.
		public UMLClass (UMLObject source, int changeType, int x, int y, int z, int line1Height, int line2Height) 
				throws Exception 
		{
			//
			super(source, changeType, x, y, z);
			this.line1Y = y + line1Height;
			this.line2Y = y + line2Height;
		}

		//**********************************************************************
		
		public int getLine1Y ()
		{
			return line1Y;
		}
		//**********************************************************************
		// We are attempting to draw this thing.
		
		public void drawClass (Graphics g) 
		{
			super.draw(g);
			g.setColor(Color.black);
			g.drawLine(x, line1Y, x + width, line1Y);
		}

	}
}
