package edu.millersville.cs.segfault.tests;

import java.util.Iterator;

import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.UMLObject;

public class ZIterationTest {

	public static void main(String[] args) throws Exception {
		UMLModel testModel = new UMLModel();
		
		testModel = testModel.add(new UMLObject().move(1, 2, 3));
		testModel = testModel.add(new UMLObject().move(1, 2, 3));
		
		testModel = testModel.add(new UMLObject().move(0, 0, 5));
		testModel = testModel.add(new UMLObject().move(50,50,9));
		
		Iterator<DrawableUML> zIter = testModel.zIterator();
		
		while (zIter.hasNext())
		{
			System.out.println(zIter.next().getZ());
		}
	}
}