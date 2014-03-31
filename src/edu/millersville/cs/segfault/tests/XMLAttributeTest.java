package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.XMLAttribute;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.UMLRelation;
import edu.millersville.cs.segfault.ui.UMLPanel;

public class XMLAttributeTest {

	@Test
	public void test() throws Exception {
		
		UMLModel testModel = new UMLModel();
		
		UMLObject testO1 = new UMLObject();
		testModel = testModel.add(testO1);
		
		
		//assertFalse(XMLAttribute.hasAttr(testO1.serialize(),testO1.getLabel()) == true);
		//assertTrue();
	}

}
