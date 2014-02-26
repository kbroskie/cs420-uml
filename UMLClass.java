public class UMLClass extends UMLObject {

	//*********************************************************************
	//Empty constructor
	public UMLClass () 
	{
		super();
	}

	//Copy constructor
	public UMLClass (UMLClass source)
	{
		super(UMLObject source);
	}

	//Dimension constructor.
	public UMLClass (UMLObject source, int changeType, int width, int height) {
		super(UMLObject source, int changeType, int width, int height);
		int line1Height = height/2;
		int line2Height = height/4;
	}
	
	//Position constructor.
	public UMLClass (UMLObject source, int changeType, int x, int y, int z) {
		super(UMLObject source, int changeType, int x, int y, int z);
		int line1Y = y + line1Height;
		int line2Y = y + line2Height;
	}
	
	//Serialization?!?!?!
	
	//Modification constructors?!??!

	//**********************************************************************

	//Mutators
	
	public UMLClass changeLabel (String newLabel)
	{
		super.changeLabel(String newLabel);
	}
}
