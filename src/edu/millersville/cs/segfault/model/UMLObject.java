package edu.millersville.cs.segfault.model;

public class UMLObject {
	
	//********************************************************************
	// Object change types.
	public static final int SET_LABEL = 0; // Change the object's label.
	//********************************************************************
	
	private String label;  // Label of the object.
	
	//********************************************************************
	// Constructors
	//********************************************************************
	
	// Empty constructor
	public UMLObject(){
		this.label = "";
	}
	
	// De-serialization constructor
	public UMLObject(String serialized) throws Exception {
			int startLabel = serialized.indexOf("<label>") + 7;
			int endLabel = serialized.lastIndexOf("</label>");
			if (startLabel == 6 || endLabel == -1) {
				throw new Exception("Object with no label!");
			} else if (startLabel == endLabel) {
				this.label = "";
			} else {
				this.label = serialized.substring(startLabel, endLabel);
			}
	}
	
	// Copy constructor
	public UMLObject(UMLObject source) 
	{
		this();
		this.label = source.getLabel();
	}
	
	// Inject string constructor
	public UMLObject(UMLObject source, int changeType, String newString)
		throws Exception
	{
		this(source);
		
		if (changeType == SET_LABEL) 
		{
			this.label = newString;
		} else {
			throw new Exception("Unrecognized string change type.");
		}
	}
	
	//********************************************************************
	// Observers
	//********************************************************************
	
	// Returns serialized version of object.
	public String serialize()
	{
		String serialString = "";
		
		serialString += "  <label>" + this.label + "</label>\n";
		
		return serialString;
	}
		
	// Returns object's label.
	public String getLabel()
	{
		return this.label;
	}

	//********************************************************************
	// Mutators
	//********************************************************************

	// Change the label.
	public UMLObject changeLabel(String newLabel)
		throws Exception
	{
		UMLObject newObject = new UMLObject(this, SET_LABEL, newLabel);
		
		return newObject;
	}
	
}