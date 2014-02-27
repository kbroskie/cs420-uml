package edu.millersville.cs.segfault.tests;

import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;

public class ModelSerializationTest {

	public static void main(String[] args) throws Exception {
		System.out.println("Beginning Serialization test.");
		UMLModel firstModel = new UMLModel();
		UMLObject anObject = (new UMLObject()).changeLabel("An object.");
				
		firstModel = firstModel.changeName("Changed Model");
		firstModel = firstModel.addObject(anObject);
		
		System.out.println("Serializing and deserializing.");
		// Serialize and De-serialize into a new model.
		
		String firstString = firstModel.serialize();
		System.out.println("First:\n" + firstModel.serialize());
		
		UMLModel secondModel = new UMLModel(firstString);
		System.out.println("Second:\n" + secondModel.serialize());
		
	}

}
