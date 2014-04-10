package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.UMLRelation;

//******************************************************************************************//
// Serialize Tests
//
// Tests Serialization.
//
// Author: Daniel Rabiega
//******************************************************************************************//

public class ModelSerializationTest {

	@Test
	public void test()
		throws Exception
	{
		UMLModel testModel = new UMLModel();
		testModel = testModel.add(new UMLObject());
		testModel = testModel.add(new UMLRelation(
				new ImmutablePath(new ImmutablePoint()), 1, false));
		System.out.println(testModel.serialize());
		UMLModel serialModel = new UMLModel(testModel.serialize());

		assertTrue("Serialized models are different!", serialModel.serialize().equals(
				testModel.serialize()));
		
		
	}

}
