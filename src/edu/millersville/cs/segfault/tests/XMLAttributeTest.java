package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.XMLAttribute;
import edu.millersville.cs.segfault.model.object.*;

//******************************************************************************************//
// Attribute Tests
//
// Tests the XMLAttribute class.
//
// Author: Benjamin Nothstein
//******************************************************************************************//

public class XMLAttributeTest {

	@Test
	public void test() throws Exception {
		
		UMLModel testModel = new UMLModel();
		
		UMLObject testO1 = new UMLObject();
		UMLState testSta1 = new UMLState();
		testModel = testModel.add(testO1);
		testModel = testModel.add(testSta1);
		
		//**********************************************************************************//
		//hasAttr Tests (may change if serialization form changes)
		//**********************************************************************************//
		
		assertTrue(XMLAttribute.hasAttr(testO1.serialize(), "OBJECT"));
		assertTrue(XMLAttribute.hasAttr(testSta1.serialize(), "STATE"));
		assertTrue(XMLAttribute.hasAttr(testO1.serialize(), "origin"));
		assertTrue(XMLAttribute.hasAttr(testO1.serialize(), "height"));
		assertTrue(XMLAttribute.hasAttr(testModel.serialize(), "title"));
		
		assertTrue(XMLAttribute.hasAttr(testModel.serialize(), "OBJECT", 29));
		assertFalse(XMLAttribute.hasAttr(testModel.serialize(), "OBJECT", 32)); //Sometimes Returns true which fails the test (Dont know why)
		
		//**********************************************************************************//
		//startAttribute Tests (Currently not working correctly)
		//**********************************************************************************//
		
		assertTrue(XMLAttribute.startAttribute(testO1.serialize(), "label") == 15);
		
		//Tests to help solve problem
		//System.out.println(testO1.serialize().indexOf(XMLAttribute.openTag("label") + 2 + 5, 0));
		//System.out.println(testO1.serialize().indexOf(XMLAttribute.openTag("label") + 2));
		//System.out.println(testO1.serialize().indexOf(XMLAttribute.openTag("label")));
		//System.out.println(testO1.serialize());
		
		//**********************************************************************************//
		//endAttribute Tests (may change if serialization form changes)
		//**********************************************************************************//
		
		assertTrue(XMLAttribute.endAttribute(testO1.serialize(), "label", 0) == 15);
		assertTrue(XMLAttribute.endAttribute(testO1.serialize(), "point", 0) == 54);
		
		//**********************************************************************************//
		//getAttribute & getIntAttribute Tests (may change if serialization form changes)
		//**********************************************************************************//
		
		assertTrue(XMLAttribute.getAttribute(testO1.serialize(), "point").equals("<x>0</x><y>0</y>"));
		assertTrue(XMLAttribute.getAttribute(testO1.serialize(), "point", 40).equals("<y>0</y>"));
		
		assertTrue(XMLAttribute.getIntAttribute(testO1.serialize(), "width") == 100);
		assertTrue(XMLAttribute.getIntAttribute(testO1.serialize(), "z") == 0);
		
		//**********************************************************************************//
		//Open/Close Tests
		//**********************************************************************************//
		
		assertTrue(XMLAttribute.openTag("origin").equals("<origin>"));
		assertTrue(XMLAttribute.closeTag("origin").equals("</origin>"));
		
		//**********************************************************************************//
		//makeTag Tests
		//**********************************************************************************//
		
		assertTrue(XMLAttribute.makeTag("YOLO", 80085).equals("<YOLO>80085</YOLO>"));
		assertTrue(XMLAttribute.makeTag("Suiseiseki", "DESU~DESU~DESU~").equals("<Suiseiseki>DESU~DESU~DESU~</Suiseiseki>"));
		
		//**********************************************************************************//
		
	}

}
