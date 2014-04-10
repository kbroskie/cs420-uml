package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import java.awt.geom.Rectangle2D;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;

import edu.millersville.cs.segfault.model.DrawableType;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;

import edu.millersville.cs.segfault.model.relation.UMLRelation;
import edu.millersville.cs.segfault.model.relation.Aggregation;
import edu.millersville.cs.segfault.model.relation.Association;
import edu.millersville.cs.segfault.model.relation.Composition;

//******************************************************************************************//
// Relation Tests
//
// Tests the Relation classes.
//
// Author: Benjamin Nothstein
//******************************************************************************************//

public class RelationTest {

	@Test
	public void test() throws Exception {
		
		UMLModel testModel = new UMLModel();
		
		//**********************************************************************************//
		//Initializing Tests
		//**********************************************************************************//
		
		UMLObject testO1 = new UMLObject();
		testModel = testModel.add(testO1);
		
		UMLRelation testR1 = new UMLRelation(new ImmutablePath(
				new ImmutablePoint(80, 10)).addLast(new ImmutablePoint(80, 150)), 2, false);
		
		UMLRelation testR2 = new UMLRelation(new ImmutablePath(
				new ImmutablePoint(10, 60)).addLast(new ImmutablePoint(150, 60)), 1, false);
		
		testModel = testModel.addRelation(testR1);
		testModel = testModel.addRelation(testR2);
		
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
		assertTrue("Relation was not selected!", testR1.isSelected());
		testR1 = testR1.unselect();
		assertFalse("Relation was not unselected!", testR1.isSelected());
		
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
		
		//**********************************************************************************//
		//isWithin Test
		//**********************************************************************************//
		
		Rectangle2D testBox = new Rectangle2D.Double(0, 0, 180, 80);
		
		assertTrue(testR2.isWithin(testBox));
		assertFalse(testR1.isWithin(testBox));

		//**********************************************************************************//
		//SnapPoint Test
		//**********************************************************************************//
		
		//UMLRelation testR3 = new UMLRelation(new ImmutablePath(
		//		new ImmutablePoint(10, 60)).addLast(testR1.snapPoint(new ImmutablePoint(90, 150))), 1, false);
		//
		//assertTrue(testR3.near(new ImmutablePoint(90, 150), 3));
		
		//**********************************************************************************//
		//Aggregation Test
		//**********************************************************************************//
		
		Aggregation testAgg1 = new Aggregation(testR1.serialize());
		Aggregation testAgg2 = new Aggregation(
				new ImmutablePath(new ImmutablePoint(50, 50)).addLast(new ImmutablePoint(100, 100)), 5, false);
		
		assertTrue(testAgg1.getType() == DrawableType.AGGREGATION);
		assertTrue(testAgg2.getType() == DrawableType.AGGREGATION);
		
		assertTrue(testAgg1.select().isSelected());
		assertFalse(testAgg1.unselect().isSelected());
		
		//**********************************************************************************//
		//Association Test
		//**********************************************************************************//
		
		Association testAss1 = new Association(testR1.serialize());
		Association testAss2 = new Association(
				new ImmutablePath(new ImmutablePoint(50, 50)).addLast(new ImmutablePoint(100, 100)), 5, false);
		
		assertTrue(testAss1.getType() == DrawableType.ASSOCIATION);
		assertTrue(testAss2.getType() == DrawableType.ASSOCIATION);
		
		assertTrue(testAss1.select().isSelected());
		assertFalse(testAss1.unselect().isSelected());
		
		//**********************************************************************************//
		//Composition Test
		//**********************************************************************************//
		
		Composition testCom1 = new Composition(testR1.serialize());
		Composition testCom2 = new Composition(
				new ImmutablePath(new ImmutablePoint(50, 50)).addLast(new ImmutablePoint(100, 100)), 5, false);
		
		assertTrue(testCom1.getType() == DrawableType.COMPOSITION);
		assertTrue(testCom2.getType() == DrawableType.COMPOSITION);
		
		assertTrue(testCom1.select().isSelected());
		assertFalse(testCom1.unselect().isSelected());
		
		//**********************************************************************************//
		
		//Not yet tested
		// - findIntAttr
	}

}
