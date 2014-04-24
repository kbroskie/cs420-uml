package edu.millersville.cs.segfault.model;

import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.object.ActiveClass;
import edu.millersville.cs.segfault.model.object.ClassObject;
import edu.millersville.cs.segfault.model.object.Collaboration;
import edu.millersville.cs.segfault.model.object.Component;
import edu.millersville.cs.segfault.model.object.FreeText;
import edu.millersville.cs.segfault.model.object.Node;
import edu.millersville.cs.segfault.model.object.Package;
import edu.millersville.cs.segfault.model.object.State;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.object.UseCase;
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
	//Name        Object?  HowManyTexts?
	OBJECT        (true,    1), 
	FREE_TEXT	  (true,    1),
	CLASS         (true,    3),
	ACTIVE_CLASS  (true,    3), 
	COMPONENT     (true,    1),
	NODE          (true,    1),
	STATE         (true,    1),
	USE_CASE	  (true,    1),
	COLLABORATION (true,    1),
	PACKAGE 	  (true,    2),
	RELATION      (false,   0), 
	AGGREGATION   (false,   0), 
	COMPOSITION   (false,   0), 
	ASSOCIATION   (false,   0);
	
	
	
	
	public final boolean      isObject;
	public final ImageIcon    icon; 
	public final ImageIcon    selectedIcon; 
	public final int          textQuantity;
	
	DrawableType(boolean isObject, int textQuantity) {
		this.isObject     = isObject;
		this.icon         = new ImageIcon("img/64/" + this.name() + ".png");
		this.selectedIcon = new ImageIcon("img/64/down/" + this.name() + ".png");
		this.textQuantity = textQuantity;
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
	
	public static DrawableUML updateLabel(DrawableUML drawable, ImmutableLabel[] labels) {
		try {
			if (drawable.getType().isObject) {
				UMLObject object = (UMLObject) drawable;
				return makeObject(labels, object.getType(), object.origin, object.size, 
								  object.z, object.selected);
			} 			
		} catch (Exception e) {
			
		}
		return drawable;
	}
	
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
		case FREE_TEXT:    return new FreeText(serial);
		case ACTIVE_CLASS: return new ActiveClass(serial);
		case CLASS:        return new ClassObject(serial);
		case COMPONENT:    return new Component(serial);
		case NODE:         return new Node(serial);
		case STATE:        return new State(serial);
		case USE_CASE:	   return new UseCase(serial);
		case COLLABORATION: return new Collaboration(serial);
		case PACKAGE: return new Package(serial);
		default:           return null;
		}
	}
	
	/**************************************************************************
	 * Produces an object of a given type with a given set of properties.
	 */
	public static UMLObject makeObject(ImmutableLabel[] text, DrawableType type, 
									   ImmutablePoint origin, Dimension size, 
									   int z, boolean selected) throws Exception {
		switch(type) {
		case OBJECT:       return new UMLObject(text, origin, z, size, selected);
		case FREE_TEXT:    return new FreeText(text, origin, z, size, selected);
		case ACTIVE_CLASS: return new ActiveClass(text, origin, z, size, selected);
		case CLASS:        return new ClassObject(text, origin, z, size, selected);
		case COMPONENT:    return new Component(text, origin, z, size, selected);
		case NODE:         return new Node(text, origin, z, size, selected);
		case STATE:        return new State(text, origin, z, size, selected);
		case USE_CASE:     return new UseCase(text, origin, z, size, selected);
		case COLLABORATION:return new Collaboration(text, origin, z, size, selected);
		case PACKAGE:      return new Package(text, origin, z, size, selected);
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
	
	public static UMLRelation makeRelation(DrawableType type, ImmutablePath path, int z, boolean selected)
			throws Exception
		{
			if (type.isObject) throw new Exception("Factory: Type is object.");
			
			switch(type) {
			case RELATION: return new UMLRelation(path, z, selected);
			case AGGREGATION: return new Aggregation(path, z, selected);
			case ASSOCIATION: return new Association(path, z, selected);
			case COMPOSITION: return new Composition(path, z, selected);
			default: return null;
			}
		}
}
 