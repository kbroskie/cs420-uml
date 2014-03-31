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
	/*************************************************************************
	 * Creates a new model with a given name and containing sets of objects and
	 * relations.
	 */
	public UMLModel(String name, ImmutableSet<UMLObject> objects, ImmutableSet<UMLRelation> relations) 
		throws Exception
	{
		this.modelName = name;
		this.objects = objects;
		this.relations = relations;
	}

	/*************************************************************************
	 * Creates an Immutable Set of UMLObjects and it's subclasses from 
	 * their serialized representations in a serialzied UMLModel
	 */
	private ImmutableSet<UMLObject> deserializeObjects(String s) 
		throws Exception
	{
		ImmutableSet<UMLObject> objects = new ImmutableSet<UMLObject>();
				
		for (DrawableType type: DrawableType.objectTypeList()) {
			int search=0;
			while (XMLAttribute.hasAttr(s, type.name(), search)) {
				objects = objects.add(DrawableType.makeObject(type, XMLAttribute.getAttribute(s, type.name(), search)));
				search = XMLAttribute.endAttribute(s, type.name(), search) + 1;
			}
		}
		return objects;
	}
	
	/*************************************************************************
	 * Creates an Immutable Set of UMLRelations and it's subclasses from 
	 * their serialized representations in a serialzied UMLModel
	 */
	private ImmutableSet<UMLRelation> deserializeRelations(String s) 
		throws Exception
	{
		ImmutableSet<UMLRelation> relations = new ImmutableSet<UMLRelation>();
		
		for (DrawableType type: DrawableType.relationTypeList()) {
			int search=0;
			while (XMLAttribute.hasAttr(s, type.name(), search)) {
				relations = relations.add(DrawableType.makeRelation(type, XMLAttribute.getAttribute(s, type.name(), search)));
				search = XMLAttribute.endAttribute(s, type.name(), search) + 1;
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
			modelString += relationIterator.next().serialize() + "\n"; 
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


	/*************************************************************************
	 * Returns the set of objects held by this model.
	 */
	public ImmutableSet<UMLObject> getObjects() {
		return this.objects;
	}

	/*************************************************************************
	 * Returns the set of relations held by this model.
	 */
	public ImmutableSet<UMLRelation> getRelations() 
	{ 
		return this.relations; 
	}
	
	/*************************************************************************
	 * Returns the highest Z value present in objects and relations in this
	 * model.
	 */
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
	
	/*************************************************************************
	 * Returns the lowest Z value present in objects and relations in this model.
	 */
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
	
	/*************************************************************************
	 * Adds a drawable of any type to this diagram.
	 */
	public UMLModel add(DrawableUML newDrawable) 
		throws Exception
	{
		if (!newDrawable.getType().isObject) {
			return addRelation((UMLRelation) newDrawable);
		}
		return addObject((UMLObject) newDrawable);
	}

	/**
	 * Returns a new model with all of the objects and relations in this one except
	 * for the passed drawable.
	 */
	public UMLModel remove(Object o)
			throws Exception
	{
		return new UMLModel(this.modelName, this.objects.remove(o), this.relations.remove(o));
	}
	
	/*************************************************************************
	 * Returns a new model where the passed drawable has been replaced by a 
	 * selected copy.
	 */
	public UMLModel select(DrawableUML drawable) 
		throws Exception
	{
		UMLModel temp = remove(drawable);
		return temp.add(drawable.select());
	}

	/*************************************************************************
	 * Returns a new model where the passed drawable has been replaced by a 
	 * deselected copy.
	 */
	public UMLModel unselect(DrawableUML drawable) 
		throws Exception
	{
		UMLModel temp = remove(drawable);
		return temp.add(drawable.unselect());
	}
	
	/*************************************************************************
	 * Returns a new model where the all the drawables are not selected.
	 */
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
	
	/*************************************************************************
	 * Returns a new model in which all selected drawables have been removed.
	 */
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
	
	/*************************************************************************
	 * Returns an Iterator<DrawableUML> object which iterates over all objects
	 * and relations in the model in order of their Z values.
	 */
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

	/*************************************************************************
	 * Returns an iterator which iterates over all of the objects in the model.
	 */
	public Iterator<UMLObject> objectIterator()
	{
		return objects.iterator();
	}
	/*************************************************************************
	 * Returns an iterator which iterates over all of the relations in the model.
	 */
	public Iterator<UMLRelation> relationIterator()
	{
		return relations.iterator();
	}
	

}
