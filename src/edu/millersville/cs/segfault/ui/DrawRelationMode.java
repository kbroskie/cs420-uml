package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import edu.millersville.cs.segfault.model.UMLModel;
import edu.millersville.cs.segfault.model.UMLObject;
import edu.millersville.cs.segfault.model.UMLRelation;

public class DrawRelationMode implements PanelInteractionMode {

	UMLPanel caller;
	boolean engaged;
	UMLObject source;
	
	public DrawRelationMode(UMLPanel caller)
	{
		this.caller = caller;
		this.engaged = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		boolean inAnchor = false;
		UMLObject clickedObject = new UMLObject();
		Iterator<UMLObject> iter = caller.model().objectIterator();
		while (iter.hasNext()) 
		{
			UMLObject current = iter.next();
			int current_x = current.getX();
			int current_y = current.getY();
			int x_dist = current_x - e.getX();
			int y_dist = current_y - e.getY();
			double distance = Math.sqrt((x_dist*x_dist)+(y_dist*y_dist));
			if (distance <= 10)
			{
				inAnchor = true;
				clickedObject = current;
			}
		}
		if (inAnchor)
		{
			if (!engaged)
			{
				engaged = true;
				source = clickedObject;
			} else {
				try {
					UMLRelation newRelation = new UMLRelation(source, clickedObject);
					UMLModel newModel = caller.model().link(newRelation);
					caller.changeModel(newModel);
				} catch (Exception ex) {
					System.out.println("Failed linking objects!");
				}
				engaged = false;
				caller.repaint();
			}
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
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g) {
		Iterator<UMLObject> oIter = caller.model().objectIterator();
		
		g.setColor(Color.BLACK);
		
		while (oIter.hasNext())
		{
			UMLObject current = oIter.next();
			int x = current.getX();
			int y = current.getY();
			
			g.setColor(Color.BLACK);
			g.fillArc(x - 10, y - 10, 20, 20, 0, 360);
			
			g.setColor(Color.WHITE);
			g.fillArc(x - 7, y - 7, 14, 14, 0, 360);
			
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
