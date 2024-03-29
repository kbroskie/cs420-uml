package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.UMLRelation;

//******************************************************************************************//
// Model Tests
//
// Tests the UMLModel class.
//
// Author: Benjamin Nothstein
//******************************************************************************************//

public class UMLModelTest {

	@Test
	public void test() throws Exception {
		
		UMLModel testModel = new UMLModel();
		
		//**********************************************************************************//
		//Initializing Tests
		//**********************************************************************************//
		
		UMLObject testO1 = new UMLObject();
		//testO1 = testO1.move(100, 100, 5);
		testO1.resize(500, 450);
		testModel = testModel.add(testO1);
		
		UMLObject testO2 = new UMLObject();
		testModel.addObject(testO2);
		
		UMLObject testO3 = new UMLObject();
//		testO3 = testO3.move(100, 100, -2);
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
		
		testModel = testModel.changeName("RainbowDash");
		assertTrue("Name Change ERROR!", testModel.getName() == "RainbowDash");
		
		testModel.getObjects();
		testModel.getRelations();
		
		assertTrue(testModel.highestZ() == 2);
		assertTrue(testModel.lowestZ() == 1);
		
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
		
		testO2 = testO2.select();
		testModel = testModel.add(testO2);
		testModel = testModel.unselectAll();
		
		Iterator<UMLObject> iterO2 = testModel.objectIterator();
		while(iterO2.hasNext())
		{
			assertFalse(iterO2.next().isSelected());
		}
		
		//**********************************************************************************//
		//Iteration Test
		//**********************************************************************************//
		
		int count = 0;
		Iterator<UMLObject> iterO = testModel.objectIterator();
		while(iterO.hasNext())
		{
			++count;
			iterO.next();
		}
		assertTrue("There should be only 2 objects remaining in testModel", count == 3);
		
		
		count = 0;
		Iterator<UMLRelation> iterR = testModel.relationIterator();
		while(iterR.hasNext())
		{
			++count;
			iterR.next();
		}
		assertTrue("There should be only 2 relations in testModel", count == 2);
		
		//**********************************************************************************//
		//Remove Tests
		//**********************************************************************************//
		
		testModel.remove(testO1);
		testModel.remove(testR1);
		
		
	}

}
