package edu.millersville.cs.segfault.ui;
/***
 * Extends PanelInteractionMode to accept user input events and add {@link DrawableUML}
 * objects to the model of the given panel.
 */


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.ObjectType;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.RelationType;
import edu.millersville.cs.segfault.model.relation.UMLRelation;

public class DrawMode extends PanelInteractionMode {

	/*************************************************************************
	 * The distance within which mouse interactions will snap to snap-points.
	 *************************************************************************/
	public static final int snapDistance=20;
	
	//*************************************************************************
	// Private Instance Variables 
	//*************************************************************************
	// General drawing variables

	private final UMLPanel panel;
	private final DrawableType drawType;
	
	//*************************************************************************
	// Constructors
	//*************************************************************************	
	
	/***
	 * Constructs a new DrawMode which interprets mouse and key actions to
	 * add {@link DrawableUML}s into the {@link UMLModel} held by a {@link UMLPanel}.
	 * @param type  The type of Drawable to add to the panel.
	 * @param panel The panel to which the Drawable will be added.
	 */	
	public DrawMode(DrawableType type, UMLPanel panel) {
		this.drawType = type;
		this.panel = panel;		
	}
	
	//*************************************************************************
	// Object Factories
	//*************************************************************************
	// Returns a UMLObject of subclass ObjectType type
	private UMLObject makeObject(ObjectType type, ImmutablePoint origin, Dimension size) {
		return new UMLObject("", origin, panel.model().highestZ() + 1, size, false);
	}
	// Returns a UMLRelation of subclass RelationType type
	private UMLRelation makeRelation(RelationType type, ImmutablePath path) {
		return new UMLRelation(path, panel.model().highestZ() + 1, false);
	}

	
	//*************************************************************************
	// Action Listener Methods
	//*************************************************************************
	

	//*************************************************************************
	// Interface Actions
	//*************************************************************************
	
	
	//*************************************************************************
	// Drawing Methods
	//*************************************************************************
	
	/***
	 * Draws any partially completed objects on it's panel.
	 */
	@Override
	public void draw(Graphics g) {
		
	}
	
	//*************************************************************************
	// Helpers
	//*************************************************************************


	@Override
	public void leaveMode() {
		// TODO Auto-generated method stub
		
	}
	
	
}
