package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.UMLRelation;

public class RelationTest {

	@Test
	public void test() {
		UMLModel testModel = new UMLModel();
		testModel = testModel.add(new UMLObject());
		//UMLObject.move(50, 50, 0);
		testModel = testModel.add(new UMLObject());
		testModel = testModel.add(new UMLRelation(new ImmutablePath(new ImmutablePoint(50, 80)), 1, false));
		
		
		System.out.println(testModel.serialize());
	}

}
