package edu.millersville.cs.segfault.model;

import java.util.Iterator;
import java.util.PriorityQueue;

import edu.millersville.cs.segfault.immutable.ImmutableSet;
import edu.millersville.cs.segfault.model.object.ObjectType;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.Aggregation;
import edu.millersville.cs.segfault.model.relation.RelationType;
import edu.millersville.cs.segfault.model.relation.UMLRelation;


/*****************************************************************************
 * 
 * @author Daniel Rabiega
 */
public class UMLModel {
	
	//*************************************************************************
	// enum translation keys
	private static final DrawableType[] drawableToRelation = {
		DrawableType.RELATION, DrawableType.AGGREGATION, DrawableType.COMPOSITION
	};
		
	private static final RelationType[] relationToDrawable = {
		RelationType.RELATION, RelationType.AGGREGATION, RelationType.COMPOSITION
	};
		
	private static final DrawableType[] drawableToObject = {
		DrawableType.OBJECT, DrawableType.CLASS, DrawableType.ACTIVE_CLASS,
		DrawableType.COMPONENT
	};
		
	private static final ObjectType[] objectToDrawable = {
		ObjectType.OBJECT, ObjectType.CLASS, ObjectType.ACTIVE_CLASS,
		ObjectType.COMPONENT
	};

	//*************************************************************************
	// Type translation methods
	//*************************************************************************
	public static boolean isRelationType(DrawableType dType) {
		for (int i=0; i<drawableToRelation.length; ++i) {
			if (drawableToRelation[i]==dType) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isObjectType(DrawableType dType) {
		for (int i=0; i<drawableToObject.length; ++i) {
			if (drawableToObject[i]==dType) {
				return true;
			}
		}
		return false;
	}
	
	public static RelationType getRelationType(DrawableType dType) {
		for (int i=0; i<drawableToRelation.length; ++i) {
			if (drawableToRelation[i] == dType) {
				return relationToDrawable[i];
			}
		}
		return null;
	}
	
	public static ObjectType getObjectType(DrawableType dType) {
		for (int i=0; i<drawableToObject.length; ++i) {
			if (drawableToObject[i]==dType) {
				return objectToDrawable[i];
			}
		}
		return null;
	}

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
		ImmutableSet<UMLObject> objects = new ImmutableSet<UMLObject>();
		while (serialized.indexOf("<object>", objectSearch) != -1) 
		{
			int startObject = serialized.indexOf("<object>", objectSearch);
			int endObject = serialized.indexOf("</object>", objectSearch);
			UMLObject newObject = new UMLObject(serialized.substring(startObject, endObject));
			objects = objects.add(newObject);
			objectSearch = endObject + 1;
		}
		this.objects = objects;
		
		// De-serialize any relations
		int relationSearch = 0;
		ImmutableSet<UMLRelation> relations = new ImmutableSet<UMLRelation>();
		while (serialized.indexOf("<relation>", relationSearch) != -1)
		{
			int startRelation = serialized.indexOf("<relation>", relationSearch);
			int endRelation = serialized.indexOf("</relation>", relationSearch);
			UMLRelation newRelation = new UMLRelation(serialized.substring(startRelation, endRelation));
			relations = relations.add(newRelation);
			relationSearch = endRelation + 1;
		}
		// De-serialize any aggregations
		relationSearch = 0;
		while (serialized.indexOf("<aggregation>", relationSearch) != -1)
		{
			int startRelation = serialized.indexOf("<aggregation>", relationSearch);
			int endRelation = serialized.indexOf("</aggregation>", relationSearch);
			UMLRelation newRelation = new Aggregation(serialized.substring(startRelation, endRelation));
			relations = relations.add(newRelation);
			relationSearch = endRelation + 1;
		}
		this.relations = relations;
	}
	

	// Descriptive Constructor
	public UMLModel(String name, ImmutableSet<UMLObject> objects, ImmutableSet<UMLRelation> relations) {
		this.modelName = name;
		this.objects = objects;
		this.relations = relations;
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
			modelString += "<relation>\n" + relationIterator.next().toString() + "</relation>\n"; 
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
	{
		return new UMLModel(this.modelName, this.objects, this.relations.add(relation));
	}
	
	public UMLModel add(DrawableUML newDrawable) {
		if (isRelationType(newDrawable.getType())) {
			return addRelation((UMLRelation) newDrawable);
		}
		return addObject((UMLObject) newDrawable);
	}

	/**
	 * Removes a drawable object.
	 * @param drawable The drawable object to be removed.
	 * @return
	 */
	public UMLModel removeObject(UMLObject oldObject) {
		return new UMLModel(this.modelName, this.objects.remove(oldObject), this.relations);
	}
	
	public UMLModel removeRelation(UMLRelation oldRelation) {
		return new UMLModel(this.modelName, this.objects, this.relations.remove(oldRelation));
	}
	
	public UMLModel remove(Object o) 
	{
		return new UMLModel(this.modelName, this.objects.remove(o), this.relations.remove(o));
	}
	
	public UMLModel select(DrawableUML drawable) {
		UMLModel temp = remove(drawable);
		return temp.add(drawable.select());
	}
	
	public UMLModel unselect(DrawableUML drawable) {
		UMLModel temp = remove(drawable);
		return temp.add(drawable.unselect());
	}
	
	public UMLModel unselectAll() {
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
	
	public UMLModel deleteSelected() {
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
