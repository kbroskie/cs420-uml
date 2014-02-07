package edu.millersville.cs.segfault.model;

public class UMLRelation {
	
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
	
	UMLObject target;
	UMLObject source;
	
	public UMLRelation(UMLObject source, UMLObject target)
	{
		this.source = source;
		this.target = target;
	}
	
	public UMLRelation(UMLModel model, String serialized)
		throws Exception
	{
		this.source = model.getObject(findIntAttr("source", serialized));
		this.target = model.getObject(findIntAttr("target", serialized));
	}
	
	public String serialize(UMLModel model) 
	{
		String relationString = "";
		
		relationString += "  <source>" + model.getId(source) + "</source>\n";
		relationString += "  <target>" + model.getId(target) + "</target>\n";
		
		return relationString;
	}
}
