package edu.millersville.cs.segfault.tests;

import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.UMLObject;

public class ModelTest {

	public static void main(String[] args) 
		throws Exception
	{

		UMLModel model = new UMLModel();
		model = model.changeName("A new model.");
		
		UMLObject object = new UMLObject();
		object = object.changeLabel("A new object.");
		
		model = model.add(object);
		
		System.out.println(model.serialize());
		
		
	}

}
