package edu.millersville.cs.segfault.model;

import java.util.LinkedList;

import edu.millersville.cs.segfault.model.object.UMLActiveClass;
import edu.millersville.cs.segfault.model.object.UMLClassObject;
import edu.millersville.cs.segfault.model.object.UMLComponent;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.Aggregation;
import edu.millersville.cs.segfault.model.relation.Association;
import edu.millersville.cs.segfault.model.relation.Composition;
import edu.millersville.cs.segfault.model.relation.UMLRelation;


/*****************************************************************************
 * An enum whose types signify the different classes which implement         *
 * DrawableUML                                                               *
 * @author Daniel Rabiega                                                    *
 *****************************************************************************/
public enum DrawableType {
	//Name        Object?
	OBJECT       (true), 
	CLASS        (true),
	ACTIVE_CLASS (true), 
	COMPONENT    (true),
	RELATION     (false), 
	AGGREGATION  (false), 
	COMPOSITION  (false), 
	ASSOCIATION  (false);
	
	public final boolean      isObject;
	
	DrawableType(boolean isObject) {
		this.isObject = isObject;
	}
	
	public static LinkedList<DrawableType> typeList() {
		LinkedList<DrawableType> types = new LinkedList<DrawableType>();
		for (DrawableType type: OBJECT.getDeclaringClass().getEnumConstants()) {
			types.add(type);
		}
		return types;
	}

	public static LinkedList<DrawableType> objectTypeList() {
		LinkedList<DrawableType> types = new LinkedList<DrawableType>();
		for (DrawableType type: OBJECT.getDeclaringClass().getEnumConstants()) {
			if (type.isObject) types.add(type);
		}
		return types;
	}
	
	public static LinkedList<DrawableType> relationTypeList() {
		LinkedList<DrawableType> types = new LinkedList<DrawableType>();
		for (DrawableType type: OBJECT.getDeclaringClass().getEnumConstants()) {
			if (!type.isObject) types.add(type);
		}
		return types;
	}
	
	//************************************************************************
	// Factories
	
	// Drawable Factories - Call a specialized factory
	public DrawableUML makeDrawable(DrawableType type) {
		//TODO Drawable factory
		return null;
	}
	
	// Object Factories - Make an object or one of it's subclasses
	public UMLObject makeObject(DrawableType type, String serial) 
		throws Exception
	{
		if (!type.isObject) {
			throw new Exception("Factory: Type is not an object.");
		}
		switch(type) {
		case OBJECT: return new UMLObject(serial);
		case ACTIVE_CLASS: return new UMLActiveClass(serial);
		case CLASS: return new UMLClassObject(serial);
		case COMPONENT: return new UMLComponent(serial);
		default: return null;
		}
	}
	
	public UMLObject   makeObject(){
		//TODO Object Factory
		return null;
	}
	
	// Relation factories - make a relation or one of it's subclasses
	public UMLRelation makeRelation(DrawableType type, String serial) 
		throws Exception
	{
		if (type.isObject) throw new Exception("Factory: Type is object.");
		
		switch(type) {
		case RELATION: return new UMLRelation(serial);
		case AGGREGATION: return new Aggregation(serial);
		case ASSOCIATION: return new Association(serial);
		case COMPOSITION: return new Composition(serial);
		default: return null;
		}
	}
	
	public UMLRelation makeRelation(){
		//TODO Relation factory
		return null;
	}
}
 