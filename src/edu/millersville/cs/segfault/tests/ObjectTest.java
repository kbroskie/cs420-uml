package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;

import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.UMLModel;

import edu.millersville.cs.segfault.model.object.Collaboration;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.object.State;
import edu.millersville.cs.segfault.model.object.Node;
import edu.millersville.cs.segfault.model.object.Component;
import edu.millersville.cs.segfault.model.object.ClassObject;
import edu.millersville.cs.segfault.model.object.ActiveClass;
import edu.millersville.cs.segfault.model.object.Package;
import edu.millersville.cs.segfault.model.object.UseCase;

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
		testO1 = testO1.setText(new ImmutableLabel("blah"), 10);
		testO2 = testO2.setText(testO1.text);
		testModel = testModel.add(testO1);
		testModel = testModel.add(testO2);
		
		assertTrue(testO1.getText() == testO2.getText());
		
		//**********************************************************************************//
		//Serialization Tests
		//**********************************************************************************//
		
		testO3 = testO3.translate(20, 20);
		UMLObject testO4 = new UMLObject(testO3.serialize());
		
		assertTrue("Failed to take in and make object from serialized form", 
				testO4.getZ() == testO3.getZ());
		
		//**********************************************************************************//
		//Move and Resize Tests
		//**********************************************************************************//
		
		testO1 = testO1.translate(20, 20);
		testO2 = testO2.translate(100, 100);
		testO1 = testO1.resize(20, 50);
		testO2 = testO2.resize(60, 70);
		testO1 = testO1.resize(60, 70);
		assertTrue(testO1.getZ() == 0);


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
		//State testSta3 = new State(new ImmutableLabel("blah"), testO3.origin, testO3.getZ(), testO3.size, false);
		
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
		//UMLUseCase tests
		//**********************************************************************************//
						
		UseCase testUse1 = new UseCase();
		UseCase testUse2 = new UseCase(testO2.serialize());
						
		assertTrue(testUse1.getType() == DrawableType.USE_CASE);
		assertTrue(testUse2.getType() == DrawableType.USE_CASE);
								
		//**********************************************************************************//
				
		//**********************************************************************************//
		//UMLCollaberation tests
		//**********************************************************************************//
						
		Collaboration testCol1 = new Collaboration();
		Collaboration testCol2 = new Collaboration(testO2.serialize());
						
		assertTrue(testCol1.getType() == DrawableType.COLLABORATION);
		assertTrue(testCol2.getType() == DrawableType.COLLABORATION);
						
		//**********************************************************************************//
		//UMLPackage tests
		//**********************************************************************************//
				
		Package testPac1 = new Package();
		Package testPac2 = new Package(testO2.serialize());  //Not working correctly(OUT OF BOUNDS EXCEPTION)
		//Package testPac3 = new Package(testO3.origin, testO3.getZ(), testO3.size, false);
				
		assertTrue(testPac1.getType() == DrawableType.PACKAGE);
		assertTrue(testPac2.getType() == DrawableType.PACKAGE);
		//assertTrue(testPac3.getType() == DrawableType.PACKAGE);
						
		//**********************************************************************************//
		
		
		
		
		
	}

}
