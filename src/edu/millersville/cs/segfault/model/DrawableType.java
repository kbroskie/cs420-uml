package edu.millersville.cs.segfault.model;

import edu.millersville.cs.segfault.model.object.UMLObject;
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
	
	//************************************************************************
	// Factories
	
	// Drawable Factories - Call a specialized factory
	public DrawableUML makeDrawable(DrawableType type) {
		//TODO Drawable factory
		return null;
	}
	
	// Object Factories - Make an object or one of it's subclasses
	public UMLObject   makeObject(){
		//TODO Object Factory
		return null;
	}
	
	// Relation factories - make a relation or one of it's subclasses
	public UMLRelation makeRelation(){
		//TODO Relation factory
		return null;
	}
}
 