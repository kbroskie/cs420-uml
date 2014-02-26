package edu.millersville.cs.segfault.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;



public class UMLModel {
	

	private String modelName;    // Name of the model.
	
	private ArrayList<UMLObject> objects;
	private HashSet<UMLRelation> relations;
	
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

	public UMLModel(UMLModel source)
	{
		this.modelName = source.getName();
		this.objects = source.getObjects();
		this.relations = source.getRelations();
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
			UMLRelation newRelation = new UMLRelation(serialized.substring(startRelation, endRelation));
			relations.add(newRelation);
			relationSearch = endRelation + 1;
		}
	}
	
	// Descriptive Constructor
	public UMLModel(String copy_name, ArrayList<UMLObject> copy_objects, HashSet<UMLRelation> copy_relations)
	{
		this.modelName = copy_name;
		this.objects = copy_objects;
		this.relations = copy_relations;
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
			modelString += "<relation>\n" + relationIterator.next().toString() + "</relation>\n"; 
		}
		
		return modelString;
	}

	// Return the given name of the model.
	public String getName() 
	{
		return this.modelName;
	}

	// Returns a copy of the set of objects
	public ArrayList<UMLObject> getObjects() {
		return new ArrayList<UMLObject>(this.objects);
	}

	public HashSet<UMLRelation> getRelations() 
	{ 
		return new HashSet<UMLRelation>(this.relations); 
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
		return new UMLModel(newName, 
				new ArrayList<UMLObject>(this.objects),
				new HashSet<UMLRelation>(this.relations));
	}

	// Adds an object to the model.
	public UMLModel add(UMLObject newObject)
	{
		ArrayList<UMLObject> newList = new ArrayList<UMLObject>(this.objects);
		newList.add(newObject);
		return new UMLModel(this.modelName, newList, new HashSet<UMLRelation>(this.relations));
	}
	
	// Adds a relation between two objects
	public UMLModel link(UMLRelation relation)
	{
		HashSet<UMLRelation> newSet = new HashSet<UMLRelation>(this.relations);
		newSet.add(relation);
		return new UMLModel(this.modelName, new ArrayList<UMLObject>(this.objects), newSet);
	}

	public UMLModel remove(DrawableUML drawable) 
	{
		ArrayList<UMLObject> newObjects = new ArrayList<UMLObject>(this.objects);
		Iterator<UMLObject> oIter = objects.iterator();
		UMLObject current = new UMLObject();
		while (oIter.hasNext()) {
			current = oIter.next();
			if (current == drawable) {
				newObjects.remove(current);
			}
		}
		
		HashSet<UMLRelation> newRelations = new HashSet<UMLRelation>(this.relations);
		Iterator<UMLRelation> rIter = relations.iterator();
		UMLRelation nowCurrent = new UMLRelation();
		while (rIter.hasNext()) {
			nowCurrent = rIter.next();
			if (nowCurrent == drawable) {
				newRelations.remove(nowCurrent);
			}
		}
		
		return new UMLModel(this.modelName, newObjects, newRelations);
	}
	
	//********************************************************************
	// Iterators
	
	public Iterator<DrawableUML> zIterator()
	{
		PriorityQueue<DrawableUML> zQueue = new PriorityQueue<DrawableUML>(1 + this.objects.size()+this.relations.size(),
				new ZComparator());
		
		Iterator<UMLObject> oIter = objects.iterator();
		while (oIter.hasNext())
		{
			zQueue.add(oIter.next());
		}
		
		Iterator<UMLRelation> rIter = relations.iterator();
		while (rIter.hasNext())
		{
			zQueue.add(rIter.next());
		}
		
		return zQueue.iterator();
	}

	public Iterator<UMLObject> objectIterator()
	{
		return objects.iterator();
	}
	
	public Iterator<UMLRelation> relationIterator()
	{
		return relations.iterator();
	}
}