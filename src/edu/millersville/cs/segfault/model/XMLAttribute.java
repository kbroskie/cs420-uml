package edu.millersville.cs.segfault.model;

/*****************************************************************************
 * A collection of static methods for serializing and de-serializing objects
 * to XML.
 * @author Daniel Rabiega
 */

public class XMLAttribute {

	/**************************************************************************
	 * Returns true if s contains an XML tag name
	 */
	public static boolean hasAttr(String s, String name) {
		return s.indexOf(openTag(name)) != -1;
	}
	
	/**************************************************************************
	 * Returns true if s contains an XML tag name at index begin or later.
	 */
	public static boolean hasAttr(String s, String name, int begin) {
		return s.indexOf(openTag(name), begin) != -1;
	}
	
	/**************************************************************************
	 * Returns the beginning of the first attribute name in s
	 */
	public static int startAttribute(String s, String name) {
		return startAttribute(s, name, 0);
	}
	
	/**************************************************************************
	 * Returns the beginning of the first attribute name in s after index begin
	 */
	public static int startAttribute(String s, String name, int begin) {
		return s.indexOf(openTag(name) + 2 + name.length(), begin);
	}
	
	/**************************************************************************
	 * Returns the end of the first attribute name in s
	 */
	public static int endAttribute(String s, String name) {
		return endAttribute(s, name, 0);
	}
	
	/**************************************************************************
	 * Returns the end of the first attribute name in s after index begin
	 */
	public static int endAttribute(String s, String name, int begin) {
		return s.indexOf(closeTag(name), begin);
	}
	
	/**************************************************************************
	 * Gets the value of the first attribute name in s
	 */
	public static String getAttribute(String s, String name) {

		return s.substring(s.indexOf(openTag(name))+2+name.length(),
						   s.indexOf(closeTag(name)));
	}
	
	/**************************************************************************
	 * Gets the value of the first attribute name in s after index start
	 */
	public static String getAttribute(String s, String name, int start) {
		return getAttribute(s.substring(start), name);
	}
	
	/**************************************************************************
	 * Gets the int value of the first attribute name in s
	 */
	public static int getIntAttribute(String s, String name) {
		return new Integer(getAttribute(s, name));
	}
	
	/**************************************************************************
	 * Wraps the given string in an attribute tag.
	 */
	public static String openTag(String name) {
		return "<" + name + ">";
	}
	
	/**************************************************************************
	 * Wraps the given string in a closing tag.
	 */
	public static String closeTag(String name) {
		return "</" + name + ">";
	}
	
	/**************************************************************************
	 * Wraps content with opening and closing tags of attribute name
	 */
	public static String makeTag(String name, String content) {
		return openTag(name) + content + closeTag(name);
	}
	
	/**************************************************************************
	 * Wraps content with opening and closing tags of attribute name
	 */
	public static String makeTag(String name, int content) {
		return makeTag(name, ""+content);
	}
}
