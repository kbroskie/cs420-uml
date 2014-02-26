package edu.millersville.cs.segfault.tests;

import java.awt.Point;

import edu.millersville.cs.segfault.model.Path;
import edu.millersville.cs.segfault.model.UMLRelation;

public class RelationSerializationTest {
	public static void main(String[] args)
		throws Exception
	{
		
		Path testPath = new Path(new Point(0,0)).addPoint(new Point(1,1));
		UMLRelation testRelation = new UMLRelation(testPath);
		UMLRelation testTwo = new UMLRelation(testRelation.serialize());
		
		System.out.println(testRelation);
		System.out.println(testTwo);
	}
}
