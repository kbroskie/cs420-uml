package edu.millersville.cs.segfault.tests;

import edu.millersville.cs.segfault.model.UMLFileOp;
import edu.millersville.cs.segfault.model.object.UMLObject;

public class ObjectTest {

	public static void main(String[] args) throws Exception {
		UMLObject testObject = new UMLObject();
		
		testObject = testObject.changeLabel("A testing object!");
		
		testObject = testObject.move(10,20,30);
		
		testObject = testObject.resize(150, 42);
		
		System.out.println(testObject.serialize());
		
		UMLFileOp.saveAs(testObject.serialize());
		testObject = UMLFileOp.load();
		
		testObject = (testObject == null) ? new UMLObject() : testObject;
		System.out.println(testObject.serialize());
	}

}
