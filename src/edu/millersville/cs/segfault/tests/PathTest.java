package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import edu.millersville.cs.segfault.model.Path;

public class PathTest {

	@Test
	public void test() {
		Path firstPath = new Path(new Point(0, 0));
		firstPath = firstPath.addPoint(new Point(5, 6));
		firstPath = firstPath.addPoint(new Point(7,7));
		Path secondPath = new Path(firstPath.toString());
		assertTrue("Serial strings incompatable!", firstPath.toString().equals(secondPath.toString()));
		assertTrue("Serial string incorrect!", firstPath.toString().equals("<path><point>0,0</point><point>5,6</point><point>7,7</point></path>"));
	}
}
