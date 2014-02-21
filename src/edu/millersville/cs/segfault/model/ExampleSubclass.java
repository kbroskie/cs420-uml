package edu.millersville.cs.segfault.model;

import java.awt.Color;
import java.awt.Graphics;

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
	public ExampleSubclass(String label, int x, int y, int z, int width, int height, boolean selected, int anAttr)
	{
		super(label, x, y, z, width, height, selected);
		
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
		return new ExampleSubclass(newLabel, this.getX(), this.getY(), this.getZ(), this.getWidth(),
				this.getHeight(), this.getSelected(), this.anAttribute);
	}
	
	public ExampleSubclass move(int newx, int newy, int newz)
	{
		return new ExampleSubclass(this.getLabel(), newx, newy, newz, this.getWidth(),
				this.getHeight(), this.getSelected(), this.anAttribute);
	}
	
	public ExampleSubclass resize(int newWidth, int newHeight)
	{
		return new ExampleSubclass(this.getLabel(), this.getX(), this.getY(), this.getZ(), newWidth,
				newHeight, this.getSelected(), this.anAttribute);
	}
	
	public ExampleSubclass select()
	{
		return new ExampleSubclass(this.getLabel(), this.getX(), this.getY(), this.getZ(), this.getWidth(),
				this.getHeight(), true, this.anAttribute);
	}
	
	public ExampleSubclass unselect()
	{
		return new ExampleSubclass(this.getLabel(), this.getX(), this.getY(), this.getZ(), this.getWidth(),
				this.getHeight(), false, this.anAttribute);
	}
	
	
	//************************************************************************
	// Drawing code.
	public void draw(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
