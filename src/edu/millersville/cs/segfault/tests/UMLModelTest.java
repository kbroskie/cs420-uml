package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Iterator;
import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.UMLRelation;
import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.ui.UMLPanel;


public class UMLModelTest {

	@Test
	public void test() throws Exception {
		
		UMLModel testModel = new UMLModel();
		
		//**********************************************************************************//
		//Initializing Tests
		//**********************************************************************************//
		
		UMLObject testO1 = new UMLObject();
		testO1 = testO1.move(100, 100, 5);
		testO1.resize(500, 450);
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
		
		testModel = testModel.changeName("RainbowDash");
		assertTrue("Name Change ERROR!", testModel.getName() == "RainbowDash");
		
		testModel.getObjects();
		testModel.getRelations();
		
		assertTrue(testModel.highestZ() == 5);
		assertTrue(testModel.lowestZ() == -2);
		
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
		
		//**********************************************************************************//
		//Iteration Test
		//**********************************************************************************//
		
		int count = 0;
		UMLObject obj1 = new UMLObject();
		Iterator<UMLObject> Oiter = testModel.objectIterator();
		while(Oiter.hasNext())
		{
			++count;
			Oiter.next();
		}
		assertTrue("There should be only 2 objects remaining in testModel", count == 2);
		
		
		UMLObject obj2 = new UMLObject();
		Iterator<UMLRelation> IterR = testModel.relationIterator();
		while(Oiter.hasNext())
		{
			++count;
			Oiter.next();
		}
		assertTrue("There should be only 2 relations in testModel", count == 2);
		
		//**********************************************************************************//
		//Remove Tests
		//**********************************************************************************//
		
		testModel.remove(testO1);
		testModel.remove(testR1);
		testModel.removeObject(testO2);
		testModel.removeRelation(testR2);
		
		//**********************************************************************************//	
		//makeObject & makeRelation Tests
		//**********************************************************************************//	
		
		UMLPanel testPanel = new UMLPanel();
		
		UMLObject testMO = DrawableType.makeObject(DrawableType.OBJECT, 
						new ImmutablePoint(0,0), testO1.getSize(), testPanel);
		
		//UMLRelation testMR = DrawableType.makeRelation(DrawableType.RELATION, 
		//				testR1.getPath(), testPanel);
		
		assertTrue("Heights do not match!", testMO.getHeight() == testO1.getHeight());
		assertTrue("Labels do not match!", testMO.getLabel() == testO1.getLabel());
		assertTrue("Types do not match!", testMO.getType() == testO1.getType());
		//assertTrue("End point did not get moved over to new Relation!", testMR.getEnd() == testR1.getEnd());
		//assertFalse("Z is not supposed to be shared!", testMR.getZ() == testR1.getZ());
		
		//**********************************************************************************//	
	}

}
