package edu.millersville.cs.segfault.model;

import java.util.Iterator;
import java.util.PriorityQueue;

import edu.millersville.cs.segfault.immutable.ImmutableSet;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.UMLRelation;


/*****************************************************************************
 * 
 * @author Daniel Rabiega
 */
public class UMLModel {
	
	//*************************************************************************
	// Instance Variables
	//*************************************************************************
	private final String modelName;    // Name of the model.
	
	private final ImmutableSet<UMLObject> objects;
	private final ImmutableSet<UMLRelation> relations;
	
	//*************************************************************************
	// Constructors	
	//*************************************************************************
	
	/**
	 * Empty constructor
	 */
	public UMLModel() 
	{ 
		modelName = "New UML Model";
		this.objects = new ImmutableSet<UMLObject>();
		this.relations = new ImmutableSet<UMLRelation>();
	}

	/**
	 * Copy constructor
	 * @param source The UMLModel to be copied.
	 */

	public UMLModel(UMLModel source)
	{
		this.modelName = source.getName();
		this.objects = source.getObjects();
		this.relations = source.getRelations();
	}
	
	// De-serialization constructor

	/**
	 * De-serialization constructor
	 * @param serialized The string to be de-serialized.
	 * @throws Exception
	 */

	public UMLModel(String serialized) throws Exception 
	{
		
		this.modelName = XMLAttribute.getAttribute(serialized, "title");
		
		
		this.objects   = deserializeObjects(serialized);
		this.relations = deserializeRelations(serialized);
	}
	

	// Descriptive Constructor
	public UMLModel(String name, ImmutableSet<UMLObject> objects, ImmutableSet<UMLRelation> relations) 
		throws Exception
	{
		this.modelName = name;
		this.objects = objects;
		this.relations = relations;
	}

	private ImmutableSet<UMLObject> deserializeObjects(String s) 
		throws Exception
	{
		ImmutableSet<UMLObject> objects = new ImmutableSet<UMLObject>();
				
		for (DrawableType type: DrawableType.objectTypeList()) {
			int search=0;
			while (XMLAttribute.hasAttr(s, type.name(), search)) {
				objects.add(DrawableType.makeObject(type, XMLAttribute.getAttribute(s, type.name(), search)));
				search = XMLAttribute.endAttribute(s, type.name(), search);
			}
		}
		return objects;
	}
	
	private ImmutableSet<UMLRelation> deserializeRelations(String s) 
		throws Exception
	{
		ImmutableSet<UMLRelation> relations = new ImmutableSet<UMLRelation>();
		
		for (DrawableType type: DrawableType.relationTypeList()) {
			int search=0;
			while (XMLAttribute.hasAttr(s, type.name(), search)) {
				relations.add(DrawableType.makeRelation(type, XMLAttribute.getAttribute(s, type.name(), search)));
				search = XMLAttribute.endAttribute(s, type.name(), search);
			}
		}
		return relations;
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
			modelString += objectIterator.next().serialize() +"\n"; 
		}
		
		// Serialize relations
		Iterator<UMLRelation> relationIterator = relations.iterator();
		while (relationIterator.hasNext())
		{
			modelString += relationIterator.next().toString() + "\n"; 
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


	// Returns a copy of the set of objects
	public ImmutableSet<UMLObject> getObjects() {
		return this.objects;
	}

	public ImmutableSet<UMLRelation> getRelations() 

	{ 
		return this.relations; 
	}
	
	public int highestZ() {
		Iterator<DrawableUML> zIter = zIterator();
		int highZ = 0;
		while (zIter.hasNext()) {
			int newZ = zIter.next().getZ();
			assert(newZ >= highZ);
			highZ = newZ;
		}
		return highZ;
	}
	
	public int lowestZ() {
		return zIterator().next().getZ();
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
		return new UMLModel(newName, this.objects, this.relations);
	}


	/**
	 * Adds an object to the model.
	 * @param newObject The new object.
	 * @return
	 * @throws Exception
	 */

	// Adds an object to the model.
	public UMLModel addObject(UMLObject newObject)
		throws Exception
	{
		return new UMLModel(this.modelName, this.objects.add(newObject), this.relations);
	}
	

	// Adds a relation between two objects

	/**
	 * Adds a relation between two objects.
	 * @param relation The relation to be added.
	 * @return
	 * @throws Exception
	 */

	public UMLModel addRelation(UMLRelation relation)
		throws Exception
	{
		return new UMLModel(this.modelName, this.objects, this.relations.add(relation));
	}
	
	public UMLModel add(DrawableUML newDrawable) 
		throws Exception
	{
		if (!newDrawable.getType().isObject) {
			return addRelation((UMLRelation) newDrawable);
		}
		return addObject((UMLObject) newDrawable);
	}

	/**
	 * Removes a drawable object.
	 * @param drawable The drawable object to be removed.
	 * @return
	 */
	public UMLModel removeObject(UMLObject oldObject) 
			throws Exception
	{
		return new UMLModel(this.modelName, this.objects.remove(oldObject), this.relations);
	}
	
	public UMLModel removeRelation(UMLRelation oldRelation) 
		throws Exception
	{
		return new UMLModel(this.modelName, this.objects, this.relations.remove(oldRelation));
	}
	
	public UMLModel remove(Object o) 
			throws Exception
	{
		return new UMLModel(this.modelName, this.objects.remove(o), this.relations.remove(o));
	}
	
	public UMLModel select(DrawableUML drawable) 
		throws Exception
	{
		UMLModel temp = remove(drawable);
		return temp.add(drawable.select());
	}
	
	public UMLModel unselect(DrawableUML drawable) 
		throws Exception
	{
		UMLModel temp = remove(drawable);
		return temp.add(drawable.unselect());
	}
	
	public UMLModel unselectAll() 
		throws Exception
	{
		UMLModel workingModel = new UMLModel(this);
		Iterator<DrawableUML> zIter = this.zIterator();
		while (zIter.hasNext()) {
			DrawableUML current = zIter.next();
			if (current.isSelected()) {
				workingModel = workingModel.unselect(current);
			}
		}
		return workingModel;
	}
	
	public UMLModel deleteSelected() 
		throws Exception
	{
		UMLModel workingModel = new UMLModel(this);
		Iterator<DrawableUML> zIter = this.zIterator();
		while (zIter.hasNext()) {
			DrawableUML current = zIter.next();
			if (current.isSelected()) {
				workingModel = workingModel.remove(current);
			}
		}
		return workingModel;
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
