public class UMLClass extends UMLObject {

	//*********************************************************************
	//Empty constructor
	//x1, y1, and z1, are the coordinates of the line to be drawn in the 
	//middle. The width will be the same as the superclass. Height?
	public UMLClass () 
	{
		super();
		this.x1 = 0;
		this.y1 = 0;
		this.z1	= 0;
	}

	//Copy constructor
	public UMLClass (UMLClass source)
	{
		super(UMLObject source);
		this.x1 = source.getX();
		this.y1 = source.getY();
		this.z1 = source.getZ();
	}

	//Modification constructor

	//**********************************************************************

	//Mutators
}
