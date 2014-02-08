package edu.millersville.cs.segfault.model;

import java.awt.Graphics;

public class UMLRelation implements DrawableUML {
	
	//*************************************************************************
	// Serialization helpers
	public static int findIntAttr(String attr, String serialized)
			throws Exception
		{
			int value;
			
			if (!serialized.contains("<" + attr + ">"))
			{
				throw new Exception("Attribute " + attr + "not found in object!");
			} else {
				value = Integer.parseInt(serialized.substring(
							serialized.indexOf("<"+attr+">") + attr.length() + 2,
							serialized.indexOf("</"+attr+">")));
			}
			
			return value;
		}
	//*************************************************************************
	
	//*************************************************************************
	// Member variables
	//*************************************************************************
	UMLObject target;
	UMLObject source;
	int z;
	
	//*************************************************************************
	// Constructors
	public UMLRelation(UMLObject source, UMLObject target)
	{
		this.source = source;
		this.target = target;
		this.z = 0;
	}
	
	public UMLRelation(UMLModel model, String serialized)
		throws Exception
	{
		this.source = model.getObject(findIntAttr("source", serialized));
		this.target = model.getObject(findIntAttr("target", serialized));
		this.z = findIntAttr("z", serialized);
	}
	
	//*************************************************************************
	// Observers
	public String serialize(UMLModel model) 
	{
		String relationString = "";
		
		relationString += "  <source>" + model.getId(source) + "</source>\n";
		relationString += "  <target>" + model.getId(target) + "</target>\n";
		relationString += "  <z>" + this.z + "</z>\n";
		
		return relationString;
	}
	
	public UMLObject getSource() { return this.source; }
	
	public UMLObject getTarget() { return this.target; }
	
	public int getZ() { return this.z; }

	public void draw(Graphics g) 
	{
		
	}
}