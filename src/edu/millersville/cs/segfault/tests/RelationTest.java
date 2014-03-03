//Tests for the class UMLRelation

package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import java.awt.geom.Rectangle2D;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.relation.UMLRelation;

public class RelationTest {

	@Test
	public void test() throws Exception {
		
		UMLModel testModel = new UMLModel();
		
		//**********************************************************************************//
		//Initializing Tests
		//**********************************************************************************//
		
		UMLObject testO1 = new UMLObject();
		testModel.add(testO1);
		
		UMLRelation testR1 = new UMLRelation(new ImmutablePath(
				new ImmutablePoint(80, 10)).addLast(new ImmutablePoint(80, 150)), 2, false);
		
		UMLRelation testR2 = new UMLRelation(new ImmutablePath(
				new ImmutablePoint(10, 60)).addLast(new ImmutablePoint(150, 60)), 1, false);
		
		testModel.addRelation(testR1);
		testModel.addRelation(testR2);
		
		//**********************************************************************************//
		//Get Tests
		//**********************************************************************************//
		
		assertTrue(testR1.getZ() == 2);
		
		System.out.println("Relation Type: " + testR1.getType());
		assertTrue("Types should be the same!", testR1.getType() == testR2.getType());
		assertFalse("Types should be different!", testR1.getType() == testO1.getType());
		
		System.out.println("Relation Class: " + testR1.getClass());
		assertTrue("Classes should be the same!", testR1.getClass() == testR2.getClass());
		
		//**********************************************************************************//
		//Select Tests
		//**********************************************************************************//
		
		testR1 = testR1.select();
		assertTrue("Object was not selected!", testR1.isSelected());
		testR1 = testR1.unselect();
		assertFalse("Object was not unselected!", testR1.isSelected());
		
		//**********************************************************************************//
		//toString Test
		//**********************************************************************************//
		
		//Relation in string form
		System.out.println("String Form: ");
		System.out.println(testR1.toString());
		
		//**********************************************************************************//
		//Hit,Near Tests
		//**********************************************************************************//
		
		assertTrue("Hit is not hitting the line drawn in testR1!", testR1.hit(new ImmutablePoint(80, 10)));
		assertTrue("Hit is not hitting the line drawn in testR1!", testR1.hit(new ImmutablePoint(80, 60)));
		assertTrue("Hit is not hitting the line drawn in testR1!", testR1.hit(new ImmutablePoint(80, 110)));
		assertFalse("Should not hit point when 10 away!", testR1.hit(new ImmutablePoint(70, 110)));
		assertTrue("Should now hit at 11 away!", testR1.near(new ImmutablePoint(70, 110), 11));
		
		//???
		//assertFalse("Hit is hitting a line that isn't draw there!", testR1.hit(new ImmutablePoint(80, 500)));
		//???
		
		//**********************************************************************************//
		//isWithin Test
		//**********************************************************************************//
		
		Rectangle2D testBox = new Rectangle2D.Double(0, 0, 100, 50);
		
		//How to add testBox to the testModel so that it is checked?
		//assertTrue(testR1.isWithin(testBox));
		//assertFalse(testR2.isWithin(testBox));
		
		//**********************************************************************************//
		//SnapPoint Test
		//**********************************************************************************//
		
		//UMLRelation testR3 = new UMLRelation(new ImmutablePath(
		//		new ImmutablePoint(10, 60)).addLast(testR1.snapPoint(new ImmutablePoint(90, 150))), 1, false);
		//
		//assertTrue(testR3.near(new ImmutablePoint(90, 150), 3));
		
		//**********************************************************************************//
		
		
		//Not yet tested
		
		// - findIntAttr
		// - draw
	}

}
