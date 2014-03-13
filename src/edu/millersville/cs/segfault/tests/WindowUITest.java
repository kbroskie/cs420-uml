package edu.millersville.cs.segfault.tests;
import static org.junit.Assert.*;

import org.junit.Test;
import java.io.File;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.UMLFileOp;
import edu.millersville.cs.segfault.ui.UMLPanel;


public class WindowUITest {

	@Test
	public void test() {
		
		UMLPanel testPanel1 = new UMLPanel();
		UMLPanel testPanel2 = new UMLPanel();
		UMLModel testModel = new UMLModel();
		UMLObject new1 = new UMLObject();
		UMLObject new2 = new UMLObject();
		testModel.add(new1);
		testModel.add(new2);
		
		
		
		//**********************************************************************************//
		//Actions Performed Tests
		//**********************************************************************************//
		
		//Change Model
		testPanel2.changeModel(testModel);
		testPanel1.changeModel(testModel);
		assertFalse(testPanel1 == testPanel2);
		
		//Save & Load
		String serialized = testModel.serialize();
		assertTrue(testPanel1.save(serialized));
		//assertTrue(testPanel1.saveAs(serialized));
		assertTrue(testPanel1.load());
		
		//Undo & Redo
		
		
	}

}
