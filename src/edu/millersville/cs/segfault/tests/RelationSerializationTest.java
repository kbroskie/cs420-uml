package edu.millersville.cs.segfault.tests;

import edu.millersville.cs.segfault.model.*;

public class RelationSerializationTest {
	public static void main(String[] args)
		throws Exception
	{
		
		UMLModel testModel = new UMLModel();
		testModel = testModel.changeName("A test model.");
		testModel = testModel.add(new UMLObject().changeLabel("First test object"));
		testModel = testModel.add(new UMLObject().changeLabel("Second test object"));
		
		testModel = testModel.link(new UMLRelation(testModel.getObject(0), testModel.getObject(1)));
		
		System.out.println(testModel.serialize());
		
		testModel = new UMLModel(testModel.serialize());
		
		System.out.println(testModel.serialize());
	}
}
