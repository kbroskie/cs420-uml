package edu.millersville.cs.segfault.model;

public class XMLAttribute {

	public static boolean hasAttr(String s, String name) {
		return s.indexOf(openTag(name)) != -1;
	}
	
	public static boolean hasAttr(String s, String name, int begin) {
		return s.indexOf(openTag(name), begin) != -1;
	}
	
	public static int startAttribute(String s, String name) {
		return startAttribute(s, name, 0);
	}
	
	public static int startAttribute(String s, String name, int begin) {
		return s.indexOf(openTag(name) + 2 + name.length(), begin);
	}
	
	public static int endAttribute(String s, String name) {
		return endAttribute(s, name, 0);
	}
	
	public static int endAttribute(String s, String name, int begin) {
		return s.indexOf(closeTag(name), begin);
	}
	
	public static String getAttribute(String s, String name) {
		return s.substring(s.indexOf(openTag(name))+2+name.length(),
						   s.indexOf(closeTag(name)));
	}
	
	public static int getIntAttribute(String s, String name) {
		return new Integer(getAttribute(s, name));
	}
	
	public static String openTag(String name) {
		return "<" + name + ">";
	}
	
	public static String closeTag(String name) {
		return "</" + name + ">";
	}
	
	
}
