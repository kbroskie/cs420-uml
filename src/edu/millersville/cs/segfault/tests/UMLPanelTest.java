package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.ui.UMLPanel;
import edu.millersville.cs.segfault.ui.UMLWindow;

//******************************************************************************************//
// Panel Tests
//
// Tests the UMLPanel class.
//
// Author: Benjamin Nothstein
//******************************************************************************************//

public class UMLPanelTest {

	@Test
	public void test() throws Exception {
		
		UMLWindow test = new UMLWindow();
		UMLModel testModel = new UMLModel();
		UMLModel testModel2 = new UMLModel();
		UMLObject testO1 = new UMLObject();
		UMLObject testO2 = new UMLObject();
		testModel = testModel.add(testO1);
		testModel2 = testModel.add(testO2);
		UMLPanel testPanel = new UMLPanel(test, testModel);
		UMLPanel testPanel2 = new UMLPanel(test, testModel2);
		
		//**********************************************************************************//
		//Save & Load Tests
		//**********************************************************************************//
		
		String serialized = testModel.serialize();
		assertTrue(testPanel.save(serialized));
		//assertTrue(testPanel.saveAs(serialized));  //same as save
		assertTrue(testPanel.load());
		
		//**********************************************************************************//
		//Undo & Redo Tests
		//**********************************************************************************//
		
		testPanel.add(testPanel2);
		System.out.println(testPanel.getComponentCount());
		testPanel.undo();
		//System.out.println(testPanel.);
		//testPanel.redo();
		
		//**********************************************************************************//
		//Selection Tests
		//**********************************************************************************//
		
		testPanel.select();
		
		//**********************************************************************************//
		//GetPerferredSize Tests
		//**********************************************************************************//
		
		System.out.println("New Panel size: ");
		System.out.println(testPanel.getSize());
		System.out.println("Preferred Size: ");
		System.out.println(testPanel.getPreferredSize());
		System.out.println("Preferred size with Object added(size 700 by 700): ");
		testO1 = testO1.resize(700, 700);
		testModel = testModel.add(testO1);
		testPanel.changeModel(testModel);
		System.out.println(testPanel.getPreferredSize());
		
		//**********************************************************************************//
	}

}
