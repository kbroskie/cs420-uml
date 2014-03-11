package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.UMLRelation;

public class UMLModelTest {

	@Test
	public void test() {
		UMLModel testModel = new UMLModel();
		
		//**********************************************************************************//
		//Initializing Tests
		//**********************************************************************************//
		
		UMLObject testO1 = new UMLObject();
		testModel.add(testO1);
		
		UMLObject testO2 = new UMLObject();
		testModel.addObject(testO2);
		
		UMLRelation testR1 = new UMLRelation(new ImmutablePath(
				new ImmutablePoint(20, 70)).addLast(new ImmutablePoint(80, 150)), 2, false);
		testModel.add(testR1);
		
		UMLRelation testR2 = new UMLRelation(new ImmutablePath(
				new ImmutablePoint(30, 20)).addLast(new ImmutablePoint(50, 200)), 1, false);
		testModel.addRelation(testR2);

		//**********************************************************************************//
		//Selection Tests
		//**********************************************************************************//
		
		
		

		//**********************************************************************************//
		//Type Check Tests
		//**********************************************************************************//
		
		assertTrue(testModel.isObjectType(testO1.getType()));
		assertFalse(testModel.isObjectType(testR1.getType()));
		
		assertFalse(testModel.isRelationType(testO1.getType()));
		assertTrue(testModel.isRelationType(testR1.getType()));
		
		
		//**********************************************************************************//
		//Remove Tests
		//**********************************************************************************//
		
		testModel.remove(testO1);
		testModel.remove(testR1);
		testModel.removeObject(testO2);
		testModel.removeRelation(testR2);
		//needs check to make sure they are removed!
		
		//**********************************************************************************//
		
		//getRelationType
		//getObjectType
		//serialize
		//getName
		//getObjects
		//getRelations
		//highestZ
		//lowestZ
		//changeName
		//select
		//unselect
		//unselectAll
		//deleteSelected
		//zIterator
		//objectIterator
		//relationIterator
		
		
		
	}

}
