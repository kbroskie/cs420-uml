package edu.millersville.cs.segfault.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.UMLObject;

public class UMLPanel extends JPanel implements MouseInputListener {
	
	
	private static final long serialVersionUID = 3691818181393202313L;
	UMLModel currentModel;
	LinkedList<UMLModel> undoStack;
	LinkedList<UMLModel> redoStack;
	PanelInteractionMode currentInteractionMode;
	int lastX;
	int lastY;
	
	enum change_types {}
	
	public UMLPanel(){
		currentModel = new UMLModel();
		undoStack = new LinkedList<UMLModel>();
		redoStack = new LinkedList<UMLModel>();
		currentInteractionMode = new DrawObjectMode(this);
		lastX = 0;
		lastY = 0;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public UMLPanel(UMLModel new_model) {
		this();
		this.changeModel(new_model);
		repaint();
	}
	
	public void changeModel(UMLModel new_model) {
		undoStack.push(currentModel);
		this.currentModel = new_model;
		redoStack = new LinkedList<UMLModel>();
	}

	public void changeInteractionMode(PanelInteractionMode newMode)
	{
		currentInteractionMode = newMode;
	}
	
	public Dimension getPreferredSize() {
		int maxX=0;
		int maxY=0;
		
		Iterator<UMLObject> oIter = currentModel.objectIterator();
		while (oIter.hasNext())
		{
			UMLObject current = oIter.next();
			int objectMaxX = current.getX() + current.getWidth();
			int objectMaxY = current.getY() + current.getHeight();
			if (maxX < objectMaxX)
			{
				maxX = objectMaxX;
			}
			if (maxY < objectMaxY)
			{
				maxY = objectMaxY;
			}
		}
		
		return new Dimension(maxX+10,maxY+10);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Iterator<DrawableUML> zIter = currentModel.zIterator();
		
		while (zIter.hasNext())
		{
			zIter.next().draw(g);
		}
		
		currentInteractionMode.draw(g, lastX, lastY);
	}
	
	public void undo()
	{
		if (undoStack.size() > 0)
		{
			redoStack.push(currentModel);
			currentModel = undoStack.pop();
		}
	}
	
	public void redo()
	{
		if (redoStack.size() > 0)
		{
			undoStack.push(currentModel);
			currentModel = redoStack.pop();
		}
	}
	
	public UMLModel model()
	{
		return currentModel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			currentInteractionMode.left_click(e.getX(), e.getY());
		} 
		if (e.getButton() == MouseEvent.BUTTON2)
		{
			currentInteractionMode.right_click(e.getX(), e.getY());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		lastX = e.getX();
		lastY = e.getY();
		repaint();
		
	}
}
