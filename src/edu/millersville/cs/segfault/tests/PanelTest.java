package edu.millersville.cs.segfault.tests;

import javax.swing.JFrame;

import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.UMLObject;
import edu.millersville.cs.segfault.model.UMLRelation;
import edu.millersville.cs.segfault.ui.UMLPanel;

public class PanelTest {
	public static void main(String[] args) throws Exception {
		
		UMLModel model = new UMLModel();
		
		UMLObject object = new UMLObject().move(10,10,0).resize(100,100);
		
		model = model.add(object);
		
		UMLObject objectB = new UMLObject().move(200,200,0).resize(50,200);
		
		model = model.add(objectB).link(new UMLRelation(object, objectB));
		
		JFrame testFrame = new JFrame("UML Panel Test");
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.add(new UMLPanel(model));
		testFrame.pack();
		testFrame.setVisible(true);
	}
}
