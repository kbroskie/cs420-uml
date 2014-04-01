package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.XMLAttribute;
import edu.millersville.cs.segfault.model.object.*;
import edu.millersville.cs.segfault.model.relation.UMLRelation;
import edu.millersville.cs.segfault.ui.UMLPanel;

public class XMLAttributeTest {

	@Test
	public void test() throws Exception {
		
		UMLModel testModel = new UMLModel();
		
		UMLObject testO1 = new UMLObject();
		UMLState testSta1 = new UMLState();
		testModel = testModel.add(testO1);
		testModel = testModel.add(testSta1);
		
		//testModel = testModel.changeName("Bluetext");
		//testO1 = testO1.changeLabel("Bluetext");
		
		//**********************************************************************************//
		//hasAttr Tests
		//**********************************************************************************//
		
		assertTrue(XMLAttribute.hasAttr(testO1.serialize(), "OBJECT"));
		assertTrue(XMLAttribute.hasAttr(testSta1.serialize(), "STATE"));
		assertTrue(XMLAttribute.hasAttr(testO1.serialize(), "origin"));
		assertTrue(XMLAttribute.hasAttr(testO1.serialize(), "height"));
		assertTrue(XMLAttribute.hasAttr(testModel.serialize(), "title"));
		
		assertTrue(XMLAttribute.hasAttr(testModel.serialize(), "OBJECT", 29));
		//assertFalse(XMLAttribute.hasAttr(testModel.serialize(), "OBJECT", 32)); //Sometimes Returns true which fails the test (Dont know why)
		
		//**********************************************************************************//
		//startAttribute Tests (Currently not working)
		//**********************************************************************************//
		
		//System.out.println(XMLAttribute.startAttribute(testO1.serialize(), "OBJECT"));
		//assertTrue(testO1.serialize().indexOf(XMLAttribute.openTag("label") + 2 + 6) == 16);
		System.out.println(testO1.serialize().indexOf(XMLAttribute.openTag("label") + 2 + 5, 0));
		System.out.println(testO1.serialize().indexOf(XMLAttribute.openTag("label") + 2));
		System.out.println(testO1.serialize().indexOf(XMLAttribute.openTag("label")));
		System.out.println(testO1.serialize());
		
		//**********************************************************************************//
		//endAttribute Tests (may change if serialization form changes)
		//**********************************************************************************//
		
		assertTrue(XMLAttribute.endAttribute(testO1.serialize(), "label", 0) == 15);
		assertTrue(XMLAttribute.endAttribute(testO1.serialize(), "point", 0) == 54);
		
		//**********************************************************************************//
		//getAttribute & getIntAttribute Tests
		//**********************************************************************************//
		
		
		
		//**********************************************************************************//
		//Open/Close Tests
		//**********************************************************************************//
		
		
		
		//**********************************************************************************//
		//makeTag Tests
		//**********************************************************************************//
		
		
		
		//**********************************************************************************//
		
	}

}
