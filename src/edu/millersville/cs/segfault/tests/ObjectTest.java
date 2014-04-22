package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;

import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.UMLModel;

import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.object.State;
import edu.millersville.cs.segfault.model.object.Node;
import edu.millersville.cs.segfault.model.object.Component;
import edu.millersville.cs.segfault.model.object.ClassObject;
import edu.millersville.cs.segfault.model.object.ActiveClass;

//******************************************************************************************//
// Object Tests
//
// Tests the UMLObject classes.
//
// Author: Benjamin Nothstein
//******************************************************************************************//

public class ObjectTest {

	@Test
	public void test() throws Exception {
		
		UMLModel testModel = new UMLModel();
		
		//**********************************************************************************//
		//Initializing Tests
		//**********************************************************************************//
		
		assertTrue(testModel.getObjects().isEmpty());
		UMLObject testO1 = new UMLObject();
		UMLObject testO2 = new UMLObject();
		UMLObject testO3 = new UMLObject();
		testModel = testModel.add(testO1);
		testModel = testModel.add(testO2);
		//assertTrue(testO1.getLabel() == testO2.getLabel());
		
		//**********************************************************************************//
		//Serialization Tests
		//**********************************************************************************//
		
		//testO3 = testO3.move(20, 20, 7);
		UMLObject testO4 = new UMLObject(testO3.serialize());
		assertTrue("Failed to take in and make object from serialized form", 
				testO4.getZ() == testO3.getZ());
		
		//**********************************************************************************//
		//Move and Resize Tests
		//**********************************************************************************//
		
//		testO1 = testO1.move(20, 20, 0);
//		testO2 = testO2.move(100, 100, 0);
		testO1 = testO1.resize(20, 50);
		testO2 = testO2.resize(60, 70);
		testO1 = testO1.resize(60, 70);
		
//		assertTrue(new1.getX() == 20);
//		assertTrue(new1.getY() == 20);
		assertTrue(testO1.getZ() == 0);
//		assertTrue(new2.getHeight() == 70);
//		assertTrue(new2.getWidth() == 60);

		
		//**********************************************************************************//
		//Selection Tests
		//**********************************************************************************//
		
		testO1 = testO1.select();
		assertTrue("Object was not selected!", testO1.isSelected());
		testO1 = testO1.unselect();
		assertFalse("Object was not unselected!", testO1.isSelected());
		
		//**********************************************************************************//
		//Label and Type tests
		//**********************************************************************************//
		
		assertTrue(testO1.getType() == DrawableType.OBJECT);
		
		//**********************************************************************************//
		//UMLState tests
		//**********************************************************************************//
		

		State testSta1 = new State();
		State testSta2 = new State(testO2.serialize());
		//State testSta3 = new State(testO3.origin, testO3.getZ(), testO3.size, false);
		
		assertTrue(testSta1.getType() == DrawableType.STATE);
		assertTrue(testSta2.getType() == DrawableType.STATE);
		//assertTrue(testSta3.getType() == DrawableType.STATE);

		
		//**********************************************************************************//
		//UMLNode tests
		//**********************************************************************************//
		
		Node testNod1 = new Node();
		Node testNod2 = new Node(testO2.serialize());
		//Node testNod3 = new Node(testO3.origin, testO3.getZ(), testO3.size, false);
		
		assertTrue(testNod1.getType() == DrawableType.NODE);
		assertTrue(testNod2.getType() == DrawableType.NODE);
		//assertTrue(testNod3.getType() == DrawableType.NODE);
		
		//**********************************************************************************//
		//UMLComponent tests
		//**********************************************************************************//
		
		Component testCom1 = new Component();
		Component testCom2 = new Component(testO2.serialize());
		//Component testCom3 = new Component(testO3.origin, testO3.getZ(), testO3.size, false);
		
		assertTrue(testCom1.getType() == DrawableType.COMPONENT);
		assertTrue(testCom2.getType() == DrawableType.COMPONENT);
		//assertTrue(testCom3.getType() == DrawableType.COMPONENT);
		
		//**********************************************************************************//
		//UMLClassObject tests
		//**********************************************************************************//
		
		ClassObject testCO1 = new ClassObject();
		ClassObject testCO2 = new ClassObject(testO2.serialize());  //Not working correctly(OUT OF BOUNDS EXCEPTION)
		//ClassObject testCO3 = new ClassObject(testO3.origin, testO3.getZ(), testO3.size, false);
		
		assertTrue(testCO1.getType() == DrawableType.CLASS);
		assertTrue(testCO2.getType() == DrawableType.CLASS);
		//assertTrue(testCO3.getType() == DrawableType.CLASS);
		
		
		//**********************************************************************************//
		//UMLActiveClass tests
		//**********************************************************************************//
		
		ActiveClass testAC1 = new ActiveClass();
		ActiveClass testAC2 = new ActiveClass(testO2.serialize());  //Not working correctly(OUT OF BOUNDS EXCEPTION)
		//ActiveClass testAC3 = new ActiveClass(testO3.origin, testO3.getZ(), testO3.size, false);
		
		assertTrue(testAC1.getType() == DrawableType.ACTIVE_CLASS);
		assertTrue(testAC2.getType() == DrawableType.ACTIVE_CLASS);
		//assertTrue(testAC3.getType() == DrawableType.ACTIVE_CLASS);
		
		//**********************************************************************************//
	}

}
