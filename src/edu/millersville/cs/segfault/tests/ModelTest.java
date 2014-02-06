package edu.millersville.cs.segfault.tests;

import edu.millersville.cs.segfault.model.UMLModel;

public class ModelTest {

	public static void main(String[] args) throws Exception {
		UMLModel firstModel = new UMLModel();
		UMLModel changedModel = firstModel.changeName("Changed Model");
		
		System.out.println("Default model: " + firstModel.serialize());
		System.out.println("Changed name:  " + changedModel.serialize());
		
	}

}
