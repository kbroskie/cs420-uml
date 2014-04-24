package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import java.awt.Font;

import org.junit.Test;
import edu.millersville.cs.segfault.immutable.ImmutableLabel;
import java.awt.Graphics;

//******************************************************************************************//
// Label Tests
//
// Tests the ImmutableLabel class.
//
// Author: Benjamin Nothstein
//******************************************************************************************//

public class LabelTest {

	@Test
	public void test() {
		
		//**********************************************************************************//
		//Label Tests
		//**********************************************************************************//

		ImmutableLabel testLabel1 = new ImmutableLabel();
		ImmutableLabel testLabel2 = new ImmutableLabel("Princess Luna", new Font("SansSerif", Font.PLAIN, 15), false);
		
		testLabel1 = testLabel1.select();
		assertTrue(testLabel1.selected);
		testLabel1 = testLabel1.deselect();
		assertFalse(testLabel1.selected);
	
		testLabel1 = testLabel1.setText(testLabel2.text);
		assertTrue("text not set correctly", testLabel1.text == "Princess Luna");
		
		testLabel1 = testLabel1.setFont(testLabel2.getFont());
		assertTrue(testLabel1.getFont() == testLabel2.getFont());
		
		//**********************************************************************************//
		
	}

}
