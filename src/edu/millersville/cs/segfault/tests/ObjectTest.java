package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;

import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.UMLModel;

import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.object.UMLState;
import edu.millersville.cs.segfault.model.object.UMLNode;
import edu.millersville.cs.segfault.model.object.UMLComponent;
import edu.millersville.cs.segfault.model.object.UMLClassObject;
import edu.millersville.cs.segfault.model.object.UMLActiveClass;

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
		
		testO3 = testO3.move(20, 20, 7);
		UMLObject testO4 = new UMLObject(testO3.serialize());
		assertTrue("Failed to take in and make object from serialized form", 
				testO4.getZ() == testO3.getZ());
		
		//**********************************************************************************//
		//Move and Resize Tests
		//**********************************************************************************//
		
		testO1 = testO1.move(20, 20, 0);
		testO2 = testO2.move(100, 100, 0);
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
		
		testO1 = testO1.changeLabel("Label1");
//		assertTrue(testO1.getLabel() == "Label1");
		
		assertTrue(testO1.getType() == DrawableType.OBJECT);
		
		//**********************************************************************************//
		//UMLState tests
		//**********************************************************************************//
		

		UMLState testSta1 = new UMLState();
		UMLState testSta2 = new UMLState(testO2.serialize());
		UMLState testSta3 = new UMLState(testO3.label, testO3.origin, testO3.getZ(), testO3.size, false);
		
		assertTrue(testSta1.getType() == DrawableType.STATE);
		assertTrue(testSta2.getType() == DrawableType.STATE);
		assertTrue(testSta3.getType() == DrawableType.STATE);

		
		//**********************************************************************************//
		//UMLNode tests
		//**********************************************************************************//
		
		UMLNode testNod1 = new UMLNode();
		UMLNode testNod2 = new UMLNode(testO2.serialize());
		UMLNode testNod3 = new UMLNode(testO3.label, testO3.origin, testO3.getZ(), testO3.size, false);
		
		assertTrue(testNod1.getType() == DrawableType.NODE);
		assertTrue(testNod2.getType() == DrawableType.NODE);
		assertTrue(testNod3.getType() == DrawableType.NODE);
		
		//**********************************************************************************//
		//UMLComponent tests
		//**********************************************************************************//
		
		UMLComponent testCom1 = new UMLComponent();
		UMLComponent testCom2 = new UMLComponent(testO2.serialize());
		UMLComponent testCom3 = new UMLComponent(testO3.label, testO3.origin, testO3.getZ(), testO3.size, false);
		
		assertTrue(testCom1.getType() == DrawableType.COMPONENT);
		assertTrue(testCom2.getType() == DrawableType.COMPONENT);
		assertTrue(testCom3.getType() == DrawableType.COMPONENT);
		
		//**********************************************************************************//
		//UMLClassObject tests
		//**********************************************************************************//
		
		UMLClassObject testCO1 = new UMLClassObject();
		UMLClassObject testCO2 = new UMLClassObject(testO2.serialize());  //Not working correctly(OUT OF BOUNDS EXCEPTION)
		UMLClassObject testCO3 = new UMLClassObject(testO3.label, testO3.origin, testO3.getZ(), testO3.size, false);
		
		assertTrue(testCO1.getType() == DrawableType.CLASS);
		assertTrue(testCO2.getType() == DrawableType.CLASS);
		assertTrue(testCO3.getType() == DrawableType.CLASS);
		
		testCO1 = testCO1.resizeClass(testCO3.size);
		assertTrue(testCO1.size == testCO3.size);
		
		testCO3 = testCO3.moveClass(new ImmutablePoint(20, 20), 69);
		assertTrue(testCO3.getZ() == 69);
		
		//**********************************************************************************//
		//UMLActiveClass tests
		//**********************************************************************************//
		
		UMLActiveClass testAC1 = new UMLActiveClass();
		UMLActiveClass testAC2 = new UMLActiveClass(testO2.serialize());  //Not working correctly(OUT OF BOUNDS EXCEPTION)
		UMLActiveClass testAC3 = new UMLActiveClass(testO3.label, testO3.origin, testO3.getZ(), testO3.size, false);
		
		assertTrue(testAC1.getType() == DrawableType.ACTIVE_CLASS);
		assertTrue(testAC2.getType() == DrawableType.ACTIVE_CLASS);
		assertTrue(testAC3.getType() == DrawableType.ACTIVE_CLASS);
		
		//**********************************************************************************//
	}

}
