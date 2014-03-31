package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;

public class PointTest {

	@Test
	public void test() {
				
		ImmutablePoint testOrigin = new ImmutablePoint();
		assertTrue("Origin point not at origin", testOrigin.getX()==0 &&
				testOrigin.getY()==0);
		
		ImmutablePoint testPoint = new ImmutablePoint(3,4);
		assertTrue("Test Point at wrong coordinates", testPoint.getX()==3 &&
				testPoint.getY()==4);
		
		assertTrue("Distance calculation incorrect.", 
				testPoint.distance(testOrigin)==5);
		
		}
}
