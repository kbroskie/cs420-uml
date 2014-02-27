package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePath;
import edu.millersville.cs.segfault.immutable.ImmutablePoint;

//****************************************************************************
// PathTest
//
// Tests the ImmutablePath class.
//
// Author: Daniel Rabiega
//****************************************************************************


public class PathTest {

	@Test
	public void test() {
		
		ImmutablePoint[] testPoints = { new ImmutablePoint(0,0), 
				new ImmutablePoint(1,1), new ImmutablePoint(2,2) };
		//Constructor Tests
		ImmutablePath arrayTest = new ImmutablePath(testPoints);
		ImmutablePath pointTest = new ImmutablePath(testPoints[0]);
		ImmutablePath serialTest = new ImmutablePath(arrayTest.toString());
		ImmutablePath copyTest = new ImmutablePath(arrayTest);
	
		assertTrue("All test paths should have 0 as x of first element.",
				testPoints[0].getX() == arrayTest.first().getX() &&
				arrayTest.first().getX() == pointTest.first().getX() &&
				pointTest.first().getX() == serialTest.first().getX() &&
				serialTest.first().getX() == copyTest.first().getX());
		
		//Ordering Test
		Iterator<ImmutablePoint> testIterator = arrayTest.iterator();
		int index = 0;
		while (testIterator.hasNext()) {
			assertTrue("Equivelent x's should be equal.", 
					testIterator.next().getX() == testPoints[index].getX() &&
					testPoints[index].getX() == serialTest.getPoints()[index].getX() &&
					arrayTest.getPoints()[index].getX() == serialTest.getPoints()[index].getX() &&
					serialTest.getPoints()[index].getX() == copyTest.getPoints()[index].getX());
			++index;
		}
		
		assertTrue("Size test failed.", pointTest.getSize()==1 &&
				pointTest.addLast(testPoints[1]).getSize()==2);
		
		assertTrue("Last test failed.", testPoints[2].getX() == arrayTest.last().getX());
		
		assertTrue("Closeness test failed", arrayTest.closestPointTo(new ImmutablePoint(3,3)).getX() == 2 &&
				arrayTest.closestPointTo(new ImmutablePoint(3,3)).getY() == 2);
		
		
		
		assertTrue("", true);
	}

}
