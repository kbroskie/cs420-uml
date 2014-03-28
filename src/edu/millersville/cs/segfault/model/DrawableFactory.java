package edu.millersville.cs.segfault.model;

import java.awt.Dimension;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.object.ObjectType;
import edu.millersville.cs.segfault.model.object.UMLActiveClass;
import edu.millersville.cs.segfault.model.object.UMLClassObject;
import edu.millersville.cs.segfault.model.object.UMLComponent;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.Aggregation;
import edu.millersville.cs.segfault.model.relation.Association;
import edu.millersville.cs.segfault.model.relation.Composition;
import edu.millersville.cs.segfault.model.relation.RelationType;
import edu.millersville.cs.segfault.model.relation.UMLRelation;
import edu.millersville.cs.segfault.ui.UMLPanel;

public class DrawableFactory {
	// *************************************************************************
	// Object Factories
	// *************************************************************************
	// Returns a UMLObject of subclass ObjectType type
	public static UMLObject makeObject(ObjectType type, ImmutablePoint origin,	Dimension size, UMLPanel panel) {			
		try {
			if (type == ObjectType.CLASS) {
				return new UMLClassObject("", origin, panel.getModel().highestZ() + 1, size, false);
			}
			if (type == ObjectType.ACTIVE_CLASS) {
				return new UMLActiveClass("", origin, panel.getModel().highestZ() + 1, size, false);
			}
			if (type == ObjectType.COMPONENT) {
				return new UMLComponent("", origin, panel.getModel().highestZ() + 1, size, false);
			}
		} catch (Exception e) {}
			return new UMLObject("", origin, panel.getModel().highestZ() + 1, size,	false);
	}

	public static UMLObject makeObject(String serialized, ObjectType type) 
		throws Exception
	{
		if (type == ObjectType.CLASS)        { return new UMLClassObject(serialized); }
		if (type == ObjectType.ACTIVE_CLASS) { return new UMLActiveClass(serialized); }
		if (type == ObjectType.COMPONENT)    { return new UMLComponent(serialized); }
		return new UMLObject(serialized);
	}
	
	
		// Returns a UMLRelation of subclass RelationType type
	public static UMLRelation makeRelation(RelationType type, ImmutablePath path, UMLPanel panel) {
		if (type == RelationType.AGGREGATION) {
			return new Aggregation(path, panel.getModel().highestZ() + 1, false);
		}
		if (type == RelationType.COMPOSITION) {
			return new Composition(path, panel.getModel().highestZ() + 1, false);
		}
		if (type == RelationType.ASSOCIATION) {
			return new Association(path, panel.getModel().highestZ() + 1, false);
		}
		return new UMLRelation(path, panel.getModel().highestZ() + 1, false);
	}
}
