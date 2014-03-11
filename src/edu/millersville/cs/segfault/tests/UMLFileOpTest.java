package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import java.io.File;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.object.UMLObject;
import edu.millersville.cs.segfault.model.UMLFileOp;

//READ
import java.io.FileReader;
import java.io.BufferedReader;


//WRITE
import java.io.FileWriter;
import java.io.BufferedWriter;

public class UMLFileOpTest {

	@Test
	public void test() {
		UMLModel testModel = new UMLModel();
		UMLObject new1 = new UMLObject();
		UMLObject new2 = new UMLObject();
		testModel.add(new1);
		testModel.add(new2);
		
		File Xfiles = new File("test.uml");
		String serialized = testModel.serialize();
		
		//saveObject(serialized, Xfiles);
	}

}
