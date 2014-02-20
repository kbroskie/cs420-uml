package edu.millersville.cs.segfault.tests;

import edu.millersville.cs.segfault.model.UMLObject;
import edu.millersville.cs.segfault.model.UMLFileOp;

public class ObjectTest {

	public static void main(String[] args) throws Exception {
		UMLObject testObject = new UMLObject();
		
		testObject = testObject.changeLabel("A testing object!");
		
		testObject = testObject.move(10,20,30);
		
		testObject = testObject.resize(150, 42);
		
		System.out.println(testObject.serialize());
		
		UMLFileOp.save(testObject.serialize());
		testObject = UMLFileOp.load();
		
		System.out.println(testObject.serialize());
	}

}
