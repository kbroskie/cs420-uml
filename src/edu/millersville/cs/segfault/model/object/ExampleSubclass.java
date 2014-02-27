package edu.millersville.cs.segfault.model.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public class ExampleSubclass extends UMLObject {

	private int anAttribute;
	
	//*******************************************************************
	//
	// Constructors
	//
	public ExampleSubclass(){
		super();  // Calls the UMLObject Constructor
		
		anAttribute = 0; //Don't forget to always initialize your attributes.
	}
	
	public ExampleSubclass(String serialized)
		throws Exception
	{
		this();
		
		this.anAttribute = UMLObject.findIntAttr("anAttribute", serialized);
	}
	
	// Attribute constructor needs all the attributes of the extended class!
	public ExampleSubclass(String label, Point origin, int z, Dimension size, boolean selected, int anAttr)
	{
		super(label, origin, z, size, selected);
		
		this.anAttribute = anAttr;
	}
	
	
	
	//**********************************************************************
	//Observers
	
	//Serialize
	public String serialize() 
	{
		String serialString = super.serialize();
		
		serialString += ("<anAttribute>" + this.anAttribute + "</anAttribute>");
		
		return serialString;
	}
	
	public int getAnAttribute() 
	{
		return this.anAttribute;
	}
	
	
	//**********************************************************************
	// Mutators
	// !!!You must override all mutators!!!
	public ExampleSubclass changeLabel(String newLabel)
	{
		return new ExampleSubclass(newLabel, this.getOrigin(), this.getZ(), 
				this.getSize(), this.isSelected(), this.anAttribute);
	}
	
	public ExampleSubclass move(Point newOrigin, int newz)
	{
		return new ExampleSubclass(this.getLabel(), newOrigin, newz, 
				this.getSize(), this.isSelected(), this.anAttribute);
	}
	
	public ExampleSubclass resize(Dimension newSize)
	{
		return new ExampleSubclass(this.getLabel(), this.getOrigin(), this.getZ(), 
				newSize, this.isSelected(), this.anAttribute);
	}
	
	public ExampleSubclass select()
	{
		return new ExampleSubclass(this.getLabel(), this.getOrigin(), this.getZ(), 
				this.getSize(), true, this.anAttribute);
	}
	
	public ExampleSubclass unselect()
	{
		return new ExampleSubclass(this.getLabel(), this.getOrigin(), this.getZ(), 
				this.getSize(), false, this.anAttribute);
	}
	
	
	//************************************************************************
	// Drawing code.
	public void draw(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
