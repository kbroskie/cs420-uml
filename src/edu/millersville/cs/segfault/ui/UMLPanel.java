package edu.millersville.cs.segfault.ui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import edu.millersville.cs.segfault.model.UMLModel;

public class UMLPanel extends JPanel {
	
	
	private static final long serialVersionUID = 3691818181393202313L;
	UMLModel currentModel;

	enum change_types {}
	
	public UMLPanel(){
		currentModel = new UMLModel();
	}

	public UMLPanel(UMLModel new_model) {
		this();
		this.changeModel(new_model);
		repaint();
	}
	
	public void changeModel(UMLModel new_model) {
		this.currentModel = new_model;
	}

	public Dimension getPreferredSize() {
		return new Dimension(250,250);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Draw test.
		g.drawString(currentModel.getName(), 10, 10);
	}
	
}
