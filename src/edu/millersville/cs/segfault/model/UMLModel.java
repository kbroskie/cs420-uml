/**
 * @author Team Segfault
 * @version 1.0
 * @since 2014-02-20
 */
package edu.millersville.cs.segfault.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


public class UMLModel {
	
	//******************************************************************
	// Change type values
	static final int SET_NAME = 0; // Change type for chaning the name
	                               // of the model.
	static final int ADD_OBJECT = 1; // Add an object to the diagram.
	static final int ADD_RELATION = 2;
	static final int REMOVE = 3;
	//******************************************************************
	
	
	private String modelName;    // Name of the model.
	
	private List<UMLObject> objects;
	private Set<UMLRelation> relations;
	
	//******************************************************************
	// Constructors	
	//******************************************************************
	
	/**
	 * Empty constructor
	 */
	public UMLModel() 
	{ 
		modelName = "New UML Model";
		this.objects = new ArrayList<UMLObject>();
		this.relations = new HashSet<UMLRelation>();
	}

	/**
	 * De-serialization constructor
	 * @param serialized The string to be de-serialized.
	 * @throws Exception
	 */
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
	
	/**
	 * Copy constructor
	 * @param source The UMLModel to be copied.
	 */
	public UMLModel(UMLModel source) 
	{
		this();
		this.modelName = source.getName();
		this.objects = new ArrayList<UMLObject>(source.getObjects());
		this.relations = new HashSet<UMLRelation>(source.getRelations());
	}
	
	/**
	 * Copy/change string constructor
	 * @param source The UMLModel to be renamed.
	 * @param changeType The action to be done.
	 * @param newString  The string the action will be done to.
	 * @throws Exception
	 */
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
	
	/**
	 * Copy + Object reference constructor.
	 * @param source The UMLModel to be copied/changed.
	 * @param changeType The action to be done.
	 * @param newObject  The object the action will be done to.
	 * @throws Exception
	 */
	public UMLModel(UMLModel source, int changeType, UMLObject newObject)
		throws Exception
	{
		this(source);
		
		if (changeType == ADD_OBJECT) 
		{
			this.objects.add(newObject);
		} else if(changeType == REMOVE) {
			objects.remove(newObject);
		} else {
			throw new Exception("Unknown change type!");
		}
	}
	
	/**
	 *  Copy + Relation reference constructor.
	 * @param source The UMLModel with the relation to be copied/changed.
	 * @param changeType The action to be done.
	 * @param newRelation The relation the action will be done to.
	 * @throws Exception
	 */
	public UMLModel(UMLModel source, int changeType, UMLRelation newRelation)
		throws Exception
	{
		this(source);
		if (changeType == ADD_RELATION) {
			this.relations.add(newRelation);
		} else if (changeType == REMOVE) {
			relations.remove(newRelation);
		} else {
			throw new Exception("Unknown change type!");
		}
	}
	
	/**
	 * Removes a drawable object.
	 * @param source The UMLModel where the object is.
	 * @param changeType The action to be done.
	 * @param newDrawable The drawable object the action will be done to.
	 */
	public UMLModel(UMLModel source, int changeType, DrawableUML newDrawable)
	{
		this(source);
		if (changeType == REMOVE) {
			objects.remove(newDrawable);
			relations.remove(newDrawable);
		}
	}
	
	
	//********************************************************************
	// Observers
	//********************************************************************
	
	/**
	 *  Turns all data in the model into a string.
	 * @return modelString The serialized string.
	 */
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

	/**
	 *  Return the given name of the model.
	 */
	public String getName() 
	{
		return this.modelName;
	}

	/**
	 * Returns a copy of the set of objects.
	 */
	public List<UMLObject> getObjects() {
		return new ArrayList<UMLObject>(this.objects);
	}

	/**
	 * Gets relations.
	 */
	public Set<UMLRelation> getRelations() 
	{ 
		return this.relations; 
	}
	
	/**
	 *  Gets the object with id n.
	 * @param n Object ID
	 * @return
	 */
	public UMLObject getObject(int n) {
		return objects.get(n);
	}
	
	/**
	 * Gets the id of a given object
	 * @param target The desired UMLObject.
	 * @return
	 */
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
	
	/**
	 *  Returns a copy of the model with a different name.
	 * @param newName The new name of the model.
	 * @return
	 * @throws Exception
	 */
	public UMLModel changeName(String newName) throws Exception 
	{
		return new UMLModel(this, SET_NAME, newName);
	}

	/**
	 * Adds an object to the model.
	 * @param newObject The new object.
	 * @return
	 * @throws Exception
	 */
	public UMLModel add(UMLObject newObject)
		throws Exception
	{
		return new UMLModel(this, ADD_OBJECT, newObject);
	}
	
	/**
	 * Adds a relation between two objects.
	 * @param relation The relation to be added.
	 * @return
	 * @throws Exception
	 */
	public UMLModel link(UMLRelation relation)
		throws Exception
	{
		return new UMLModel(this, ADD_RELATION, relation);
	}
	/**
	 * Removes a drawable object.
	 * @param drawable The drawable object to be removed.
	 * @return
	 */
	public UMLModel remove(DrawableUML drawable) 
	{
		return new UMLModel(this, REMOVE, drawable);
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