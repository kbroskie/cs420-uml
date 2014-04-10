package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import edu.millersville.cs.segfault.immutable.ImmutableSet;

//******************************************************************************************//
// Set Tests
//
// Tests the ImmutableSet class.
//
// Author: Daniel Rabiega
//******************************************************************************************//

public class SetTest {

	@Test
	public void test() {
		
		ImmutableSet<Integer> testSet = new ImmutableSet<Integer>();
		
		assertTrue("Set created with empty constructor should have size zero", testSet.size()==0);
		assertTrue("Set created with empty constructor should be empty", testSet.isEmpty());
		
		testSet = testSet.add(1).add(2);
		assertFalse("Set with items added should not be empty.", testSet.isEmpty());
		
		assertTrue("Empty set with two items added should have size two.", testSet.size()==2);
		
		testSet = testSet.remove(1);
		
		assertTrue("Size two set with one item removed should have size one." ,testSet.size()==1);
		
		Iterator<Integer> testIter = testSet.iterator();
		assertTrue("Set should contain Integer 2.", testIter.next()==2);
		
	}

}
