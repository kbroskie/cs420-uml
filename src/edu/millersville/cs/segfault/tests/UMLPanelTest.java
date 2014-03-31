package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;

import edu.millersville.cs.segfault.ui.UMLPanel;
import edu.millersville.cs.segfault.ui.WindowController;
import edu.millersville.cs.segfault.ui.DrawMode;
import edu.millersville.cs.segfault.ui.PanelInteractionMode;
import edu.millersville.cs.segfault.ui.SelectionMode;
import edu.millersville.cs.segfault.ui.UMLWindow;

public class UMLPanelTest {

	@Test
	public void test() throws Exception {
		
		
		UMLModel testModel = new UMLModel();
		UMLObject new1 = new UMLObject();
		UMLObject new2 = new UMLObject();
		testModel = testModel.add(new1);
		testModel = testModel.add(new2);
		UMLPanel testPanel = new UMLPanel(testModel);
		UMLPanel testPanel2 = new UMLPanel(testModel);
		
		//**********************************************************************************//
		//Window UI Actions Tests
		//**********************************************************************************//
		
		//Save & Load
		String serialized = testModel.serialize();
		assertTrue(testPanel.save(serialized));
		//assertTrue(testPanel.saveAs(serialized));  //same as save
		assertTrue(testPanel.load());
		
		//Undo & Redo		
		
		//GetPerferredSize
		System.out.println("New Panel size: ");
		System.out.println(testPanel.getSize());
		System.out.println("Preferred Size: ");
		System.out.println(testPanel.getPreferredSize());
		System.out.println("Preferred size with Object added(size 700 by 700): ");
		new1 = new1.resize(700, 700);
		testModel = testModel.add(new1);
		testPanel.changeModel(testModel);
		System.out.println(testPanel.getPreferredSize());
		
		//**********************************************************************************//
	}

}
