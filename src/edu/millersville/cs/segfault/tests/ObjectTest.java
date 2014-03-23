//Tests for the class UMLObject

package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;

public class ObjectTest {

	@Test
	public void test() throws Exception {
		UMLModel testModel = new UMLModel();
		
		//**********************************************************************************//
		//Initializing Tests
		//**********************************************************************************//
		
		assertTrue(testModel.getObjects().isEmpty());
		UMLObject new1 = new UMLObject();
		UMLObject new2 = new UMLObject();
		UMLObject new3 = new UMLObject();
		testModel = testModel.add(new1);
		testModel = testModel.add(new2);
		assertTrue(new1.getLabel() == new2.getLabel());
		
		//**********************************************************************************//
		//Serialization Tests
		//**********************************************************************************//
		
		new3 = new3.move(20, 20, 0);
		UMLObject new4 = new UMLObject(new3.serialize());
		assertTrue("Failed to take in and make object from serialized form", 
				new4.getX() == new3.getX());
		
		
		//**********************************************************************************//
		//Move and Resize Tests
		//**********************************************************************************//
		
		new1 = new1.move(20, 20, 0);
		new2 = new2.move(100, 100, 0);
		new1 = new1.resize(20, 50);
		new2 = new2.resize(60, 70);
		new1 = new1.resize(60, 70);
		
		assertTrue(new1.getX() == 20);
		assertTrue(new1.getY() == 20);
		assertTrue(new1.getZ() == 0);
		assertTrue(new2.getHeight() == 70);
		assertTrue(new2.getWidth() == 60);
		
		//**********************************************************************************//
		//Selection Tests
		//**********************************************************************************//
		
		new1 = new1.select();
		assertTrue("Object was not selected!", new1.isSelected());
		new1 = new1.unselect();
		assertFalse("Object was not unselected!", new1.isSelected());
		
		//**********************************************************************************//
		//Label tests
		//**********************************************************************************//
		
		new1 = new1.changeLabel("Label1");
		assertTrue(new1.getLabel() == "Label1");
		
		//**********************************************************************************//
		
	}

}
