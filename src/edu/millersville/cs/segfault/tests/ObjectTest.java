package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;

public class ObjectTest {

	@Test
	public void test() throws Exception {
		UMLModel testModel = new UMLModel();
		
		assertTrue(testModel.getObjects().isEmpty());
		UMLObject new1 = new UMLObject();
		UMLObject new2 = new UMLObject();
		//testModel.addObject(new1);
		//testModel.addObject(new2);
		testModel.add(new1);
		testModel.add(new2);
		assertTrue(new1.getLabel() == new2.getLabel());
		
		
		//Move and Resize Tests
		new1 = new1.move(20, 20, 0);
		new2 = new2.move(100, 100, 0);
		new1 = new1.resize(20, 50);
		new2 = new2.resize(60, 70);
		new1 = new1.resize(60, 70);
		
		assertTrue(new1.getX() == 20);
		assertTrue(new1.getY() == 20);
		assertTrue(new1.getZ() == 0);
		assertTrue(new2.getHeight() == 70);
		assertTrue(new2.getWidth() == 60);
		//assertTrue(new1.getSize() == new2.getSize());
		
		
		//Selection Tests
		new1 = new1.select();
		assertTrue("Object was not selected!", new1.isSelected());
		new1 = new1.unselect();
		assertFalse("Object was not unselected!", new1.isSelected());
		
		//Label tests
		new1 = new1.changeLabel("Label1");
		assertTrue(new1.getLabel() == "Label1");
		
		
		//assertTrue("", new1.within());
		
		
		
		
		
		
		//System.out.println(testModel.add(new1));
		
		
		
		//testModel.removeObject(oldObject);
		/*
		int i = 10;
		while(testModel.getObjects().iterator().hasNext())
		{
			testModel.getObjects().iterator().next().move(5*i, 5*i, 5*i);
			System.out.print(testModel.getObjects().iterator().next().getOrigin());
			
			
			i = i + 10;
		}
		*/
		//testModel.getObjects().iterator().next().getX();
		
		
		//System.out.println(testModel.getObjects());
		//testModel = testModel.move(30, 20, 0);
		
	}

}
