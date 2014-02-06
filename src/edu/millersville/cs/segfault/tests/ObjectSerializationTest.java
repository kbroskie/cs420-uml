package edu.millersville.cs.segfault.tests;

import edu.millersville.cs.segfault.model.UMLObject;

public class ObjectSerializationTest {

	public static void main(String[] args) 
		throws Exception
	{

		UMLObject firstObject = (new UMLObject()).changeLabel("First");
		UMLObject secondObject = new UMLObject(firstObject.serialize());
		
		System.out.println(firstObject.serialize());
		System.out.println(secondObject.serialize());
		
	}

}
