package edu.millersville.cs.segfault.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UMLModel {
	
	//******************************************************************
	// Change type values
	static final int SET_NAME = 0; // Change type for chaning the name
	                               // of the model.
	static final int ADD_OBJECT = 1; // Add an object to the diagram.
	//******************************************************************
	
	
	private String modelName;    // Name of the model.
	
	private Set<UMLObject> objects;
	
	//******************************************************************
	// Constructors	
	//******************************************************************
	
	// Empty constructor
	public UMLModel() 
	{ 
		modelName = "New UML Model";
		this.objects = new HashSet<UMLObject>();
	}

	// De-serialization constructor
	public UMLModel(String serialized) throws Exception 
	{
		this();
		
		// De-serialize title
		int startTitle = serialized.indexOf("<title>") + 7;
		int endTitle = serialized.indexOf("</title>");
		if (startTitle == 6 || endTitle == -1) {
			throw new Exception("Model has no title!");
		} else {
			this.modelName = serialized.substring(startTitle, endTitle);
		}
		
		// De-serialize any objects
		int objectSearch = 0;
		while (serialized.indexOf("<object>", objectSearch) != -1 && objectSearch != -1) 
		{
			UMLObject newObject = new UMLObject(serialized.substring(
							serialized.indexOf("<object>", objectSearch) + 8,
							serialized.indexOf("</object>", objectSearch)));
			objects.add(newObject);
			objectSearch = serialized.indexOf("</objects>", objectSearch);
		}
	}
	
	// Copy constructor
	public UMLModel(UMLModel source) 
	{
		this();
		this.modelName = source.getName();
		this.objects = new HashSet<UMLObject>(source.getSet());
	}
	
	// Copy/change string constructor
	public UMLModel(UMLModel source, int changeType, String newString) 
			throws Exception 
	{
		this(source);
		if (changeType == SET_NAME) {
			this.modelName = newString;
		} else {
			throw new Exception("Invalid change type / parameter to constructor.");
		}
	}
	
	// Copy + Object reference constructor
	public UMLModel(UMLModel source, int changeType, UMLObject newObject)
		throws Exception
	{
		this(source);
		
		if (changeType == ADD_OBJECT) 
		{
			this.objects.add(newObject);
		} else {
			throw new Exception("Unknown change type!");
		}
	}
	
	//********************************************************************
	// Observers
	//********************************************************************
	
	// Turns all data in the model into a string.
	public String serialize() 
	{
		String modelString = "";
		
		// Serialize title
		modelString = modelString + "<title>" + modelName + "</title>\n";
		
		// Serialize objects
		Iterator<UMLObject> objectIterator = objects.iterator();
		while (objectIterator.hasNext()) 
		{
			modelString += ("<object>\n" + objectIterator.next().serialize() + "</object>\n"); 
		}
		
		return modelString;
	}

	// Return the given name of the model.
	public String getName() 
	{
		return this.modelName;
	}

	// Returns a copy of the set of objects
	public Set<UMLObject> getSet() {
		return new HashSet<UMLObject>(this.objects);
	}
	
	//********************************************************************
	// Mutators
	//********************************************************************
	
	// Returns a copy of the model with a different name.
	public UMLModel changeName(String newName) throws Exception 
	{
		return new UMLModel(this, SET_NAME, newName);
	}

	// Adds an object to the model.
	public UMLModel add(UMLObject newObject)
		throws Exception
	{
		return new UMLModel(this, ADD_OBJECT, newObject);
	}
	
}