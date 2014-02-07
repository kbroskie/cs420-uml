package edu.millersville.cs.segfault.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class UMLModel {
	
	//******************************************************************
	// Change type values
	static final int SET_NAME = 0; // Change type for chaning the name
	                               // of the model.
	static final int ADD_OBJECT = 1; // Add an object to the diagram.
	static final int ADD_RELATION = 2;
	//******************************************************************
	
	
	private String modelName;    // Name of the model.
	
	private List<UMLObject> objects;
	private Set<UMLRelation> relations;
	
	//******************************************************************
	// Constructors	
	//******************************************************************
	
	// Empty constructor
	public UMLModel() 
	{ 
		modelName = "New UML Model";
		this.objects = new ArrayList<UMLObject>();
		this.relations = new HashSet<UMLRelation>();
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
		while (serialized.indexOf("<object>", objectSearch) != -1) 
		{
			int startObject = serialized.indexOf("<object>", objectSearch);
			int endObject = serialized.indexOf("</object>", objectSearch);
			UMLObject newObject = new UMLObject(serialized.substring(startObject, endObject));
			objects.add(newObject);
			objectSearch = endObject + 1;
		}
		
		// De-serialize any relations
		int relationSearch = 0;
		while (serialized.indexOf("<relation>", relationSearch) != -1)
		{
			int startRelation = serialized.indexOf("<relation>", relationSearch);
			int endRelation = serialized.indexOf("</relation>", relationSearch);
			UMLRelation newRelation = new UMLRelation(this, serialized.substring(startRelation, endRelation));
			relations.add(newRelation);
			relationSearch = endRelation + 1;
		}
	}
	
	// Copy constructor
	public UMLModel(UMLModel source) 
	{
		this();
		this.modelName = source.getName();
		this.objects = new ArrayList<UMLObject>(source.getObjects());
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
	
	// Copy + Relation reference constructor
	public UMLModel(UMLModel source, int changeType, UMLRelation newRelation)
		throws Exception
	{
		this(source);
		if (changeType == ADD_RELATION) {
			this.relations.add(newRelation);
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
		
		// Serialize relations
		Iterator<UMLRelation> relationIterator = relations.iterator();
		while (relationIterator.hasNext())
		{
			modelString += "<relation>\n" + relationIterator.next().serialize(this) + "</relation>\n"; 
		}
		
		return modelString;
	}

	// Return the given name of the model.
	public String getName() 
	{
		return this.modelName;
	}

	// Returns a copy of the set of objects
	public List<UMLObject> getObjects() {
		return new ArrayList<UMLObject>(this.objects);
	}

	// Gets the object with id n
	public UMLObject getObject(int n) {
		return objects.get(n);
	}
	
	// Gets the id of a given object
	public int getId(UMLObject target)
	{
		for (int i=0; i<objects.size(); ++i)
		{
			if (objects.get(i) == target)
			{
				return i;
			}
		}
		return -1;
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
	
	// Adds a relation between two objects
	public UMLModel link(UMLRelation relation)
		throws Exception
	{
		return new UMLModel(this, ADD_RELATION, relation);
	}
}