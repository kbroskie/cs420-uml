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
	public void test() throws Exception {
		
		UMLModel testModel = new UMLModel();
		
		//**********************************************************************************//
		//Initializing Tests
		//**********************************************************************************//
		
		UMLObject testO1 = new UMLObject();
		testO1 = testO1.move(100, 100, 5);
		testModel = testModel.add(testO1);
		
		UMLObject testO2 = new UMLObject();
		testModel.addObject(testO2);
		
		UMLObject testO3 = new UMLObject();
		testO3 = testO3.move(100, 100, -2);
		testModel = testModel.add(testO3);
		
		UMLRelation testR1 = new UMLRelation(new ImmutablePath(
				new ImmutablePoint(20, 70)).addLast(new ImmutablePoint(80, 150)), 2, false);
		testModel = testModel.add(testR1);
		
		UMLRelation testR2 = new UMLRelation(new ImmutablePath(
				new ImmutablePoint(30, 20)).addLast(new ImmutablePoint(50, 200)), 1, false);
		testModel = testModel.addRelation(testR2);

		//**********************************************************************************//
		//Manipulation and Observation Tests
		//**********************************************************************************//
		
		testModel = testModel.changeName("MLP");
		assertTrue("Name Change ERROR!", testModel.getName() == "MLP");
		
		testModel.getObjects();
		testModel.getRelations();
		
		assertTrue(testModel.highestZ() == 5);
		assertTrue(testModel.lowestZ() == -2);
		
		System.out.print("UMLObject Type: ");
		System.out.println(testModel.getObjectType(testO1.getType()));
		
		System.out.print("UMLRelation Type: ");
		System.out.println(testModel.getRelationType(testR1.getType()));
		
		//**********************************************************************************//
		//Selection Tests
		//**********************************************************************************//
		
		testO1 = testO1.select();
		assertTrue(testO1.isSelected());
		testO1 = testO1.unselect();
		assertFalse(testO1.isSelected());
		
		testO3 = testO3.select();
		assertTrue(testO3.isSelected());
		testModel.deleteSelected();
		
		//???
		//after deletion, the Object "testO3" can still be referenced? Might be because of undo/redo but unsure.
		//assertTrue(testO3.isSelected());
		//System.out.println(testO3.getSize());
		//???
		
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
		
		//int count = 0;
		//do {
		//	count++;
		//	testModel.objectIterator().next();
		//} while (testModel.objectIterator().hasNext());
		//System.out.println(count);
		
		testModel.remove(testO1);
		testModel.remove(testR1);
		testModel.removeObject(testO2);
		testModel.removeRelation(testR2);
		
		//**********************************************************************************//						
	}

}
