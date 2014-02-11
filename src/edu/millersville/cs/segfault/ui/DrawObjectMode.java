package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import edu.millersville.cs.segfault.model.UMLObject;


public class DrawObjectMode implements PanelInteractionMode 
{

	boolean engaged;
	int start_x;
	int start_y;
	UMLPanel caller;
	int last_x;
	int last_y;
	
	public DrawObjectMode(UMLPanel caller)
	{
		engaged = false;
		this.caller = caller;
	}

	public void draw(Graphics g)
	{
		if (engaged) {
			g.setColor(Color.GRAY);
			g.drawRect(start_x, start_y, last_x - start_x, last_y - start_y);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!engaged) 
		{
			engaged = true;
			start_x = e.getX();
			start_y = e.getY();
			last_x = start_x;
			last_y = start_y;
		} else {
			engaged = false;
			try {
				UMLObject newObject = new UMLObject().move(start_x, start_y, 1);
				int width = e.getX() - start_x;
				int height = e.getY() - start_y;
				newObject = newObject.resize(width, height);
				caller.changeModel(caller.model().add(newObject));
			} catch (Exception ex) {
				System.out.println("Exception while adding object!");
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
		this.last_x = e.getX();
		this.last_y = e.getY();
		caller.repaint();
		
	}
}
