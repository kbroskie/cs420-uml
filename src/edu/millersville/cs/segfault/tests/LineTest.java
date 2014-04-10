package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutablePoint;
import edu.millersville.cs.segfault.immutable.ImmutableLine;

//******************************************************************************************//
// Line Tests
//
// Tests the ImmutableLine class.
//
// Author: Benjamin Nothstein
//******************************************************************************************//

public class LineTest {

	@Test
	public void test() {
		
		//**********************************************************************************//
		//Initializing Tests
		//**********************************************************************************//
		
		ImmutableLine testLine1 = new ImmutableLine(
				new ImmutablePoint(10, 20), new ImmutablePoint(10, 50));
		
		ImmutableLine testLine2 = new ImmutableLine(testLine1.serialize("Harry Dresden"));
		
		ImmutableLine testLine3 = new ImmutableLine(
				new ImmutablePoint(20, 40), new ImmutablePoint(30, 50));
		
		//**********************************************************************************//
		//Length and Slope Tests
		//**********************************************************************************//
		
		assertTrue("Length of line not correct", testLine2.length() == 30);
		assertTrue("Slope not correct", testLine2.slope() == 0);
		assertTrue("Slope not correct", testLine3.slope() == 1);
		
		//**********************************************************************************//
		//Snapping Tests
		//**********************************************************************************//
		
		testLine2.snapPoint(new ImmutablePoint(30, 50));
		testLine3.snapAtX(0);
		testLine3.snapAtY(0);
		
		//**********************************************************************************//
		//Perpendicular, Intercept, and Distance Tests
		//**********************************************************************************//
		
		assertTrue("", testLine1.distance(testLine3.second) == 20);
		assertTrue("", testLine1.perpendicular(testLine3.second) == null);
		
		ImmutableLine testLine4 = new ImmutableLine(
				new ImmutablePoint(10, 20), new ImmutablePoint(50, 20));
		
		//assertTrue("", testLine4.perpendicular(testLine3.second));
		
		//**********************************************************************************//
		
		//testing item
		System.out.println();
		
		//snap at y
		//snap at x
		//snap point
		//perpendicular
		//intercept
	}

}
