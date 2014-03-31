package edu.millersville.cs.segfault.model;

import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.object.UMLActiveClass;
import edu.millersville.cs.segfault.model.object.UMLClassObject;
import edu.millersville.cs.segfault.model.object.UMLComponent;
import edu.millersville.cs.segfault.model.object.UMLNode;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.object.UMLState;
import edu.millersville.cs.segfault.model.relation.Aggregation;
import edu.millersville.cs.segfault.model.relation.Association;
import edu.millersville.cs.segfault.model.relation.Composition;
import edu.millersville.cs.segfault.model.relation.UMLRelation;
import edu.millersville.cs.segfault.ui.UMLPanel;


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
	NODE         (true),
	STATE        (true),
	RELATION     (false), 
	AGGREGATION  (false), 
	COMPOSITION  (false), 
	ASSOCIATION  (false);
	
	
	public final boolean      isObject;
	public final ImageIcon    icon; 
	
	DrawableType(boolean isObject) {
		this.isObject = isObject;
		this.icon     = new ImageIcon("img/64/" + this.name() + ".png");
	}
	
	/************************************************************************* 
	 * 	 A simple method for returning all of the types in this enum.
	 */
	public static LinkedList<DrawableType> typeList() {
		LinkedList<DrawableType> types = new LinkedList<DrawableType>();
		for (DrawableType type: OBJECT.getDeclaringClass().getEnumConstants()) {
			types.add(type);
		}
		return types;
	}

	/*************************************************************************
	 * Returns all of the types in this enum which coorespond to Objects
	 */
	public static LinkedList<DrawableType> objectTypeList() {
		LinkedList<DrawableType> types = new LinkedList<DrawableType>();
		for (DrawableType type: OBJECT.getDeclaringClass().getEnumConstants()) {
			if (type.isObject) types.add(type);
		}
		return types;
	}
	
	/*************************************************************************
	 * Returns all of the types in this enum which coorespond to Relations
	 */
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
	
	
	// Object Factories - Make an object or one of it's subclasses
	/*************************************************************************
	 * Produces an object of a given type from a given serial string.
	 */
	public static UMLObject makeObject(DrawableType type, String serial) 
		throws Exception
	{
		if (!type.isObject) {
			throw new Exception("Factory: Type is not an object.");
		}
		switch(type) {
		case OBJECT:       return new UMLObject(serial);
		case ACTIVE_CLASS: return new UMLActiveClass(serial);
		case CLASS:        return new UMLClassObject(serial);
		case COMPONENT:    return new UMLComponent(serial);
		case NODE:         return new UMLNode(serial);
		case STATE:        return new UMLState(serial);
		default:           return null;
		}
	}
	
	/**************************************************************************
	 * Produces an object of a given type with a given set of properties.
	 */
	public static UMLObject makeObject(DrawableType type, ImmutablePoint origin,
									   Dimension size, UMLPanel panel)
		throws Exception {
		if (!type.isObject) {
			throw new Exception("Factory: Type is not an object.");
		}
		switch(type) {
		case OBJECT:       return new UMLObject("", origin, panel.getModel().highestZ() + 1, size, false);
		case ACTIVE_CLASS: return new UMLActiveClass("", origin, panel.getModel().highestZ() + 1, size, false);
		case CLASS:        return new UMLClassObject("", origin, panel.getModel().highestZ() + 1, size, false);
		case COMPONENT:    return new UMLComponent("", origin, panel.getModel().highestZ() + 1, size, false);
		case NODE:         return new UMLNode("", origin, panel.getModel().highestZ() + 1, size, false);
		case STATE:        return new UMLState("", origin, panel.getModel().highestZ() + 1, size, false);
		default:           return null;
		}
	}
	
	
	// Relation factories - make a relation or one of it's subclasses
	/*************************************************************************
	 * Produces a relation of a given type from a given serial string.
	 */
	public static UMLRelation makeRelation(DrawableType type, String serial) 
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
	
	/*************************************************************************
	 * Produces a relation of a given type with given properties.
	 */
	public static UMLRelation makeRelation(DrawableType type, ImmutablePath path, UMLPanel panel)
		throws Exception
	{
		if (type.isObject) throw new Exception("Factory: Type is object.");
		
		switch(type) {
		case RELATION: return new UMLRelation(path, panel.getModel().lowestZ()-1, false);
		case AGGREGATION: return new Aggregation(path, panel.getModel().lowestZ()-1, false);
		case ASSOCIATION: return new Association(path, panel.getModel().lowestZ()-1, false);
		case COMPOSITION: return new Composition(path, panel.getModel().lowestZ()-1, false);
		default: return null;
		}
	}
	
}
 