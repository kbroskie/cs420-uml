package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.ZComparator;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.UMLRelation;

//******************************************************************************************//
// ZComparator Tests
//
// Tests the ZComparator class.
//
// Author: Benjamin Nothstein
//******************************************************************************************//

public class ZComparatorTest {

	@Test
	public void test() throws Exception {
		
		//**********************************************************************************//
		//Initialization
		//**********************************************************************************//
		
		ZComparator testZ = new ZComparator();
		
		UMLObject testO = new UMLObject();
		testO = testO.translate(100, 100);
		
		UMLRelation testR = new UMLRelation(new ImmutablePath(
				new ImmutablePoint(20, 70)).addLast(new ImmutablePoint(80, 150)), 4, false);
		
		//**********************************************************************************//
		//Z Compare on Objects and Relations Tests
		//**********************************************************************************//
		
		assertTrue("ZComparator for Drawable A > B Failed!", testZ.compare(testO, testR) == 1);
		
//		testO = testO.move(100, 100, 3);
		
		assertTrue("ZComparator for Drawable A < B Failed!", testZ.compare(testO, testR) == -1);
		
//		testO = testO.move(100, 100, 4);
		
		assertTrue("ZComparator for Drawable A = B Failed!", testZ.compare(testO, testR) == 0);

		//**********************************************************************************//
		
	}

}
