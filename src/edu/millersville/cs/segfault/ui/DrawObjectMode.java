package edu.millersville.cs.segfault.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
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
				UMLObject newObject;
				last_x = e.getX();
				last_y = e.getY();
				
				/*** Is the second x to the left of the first? ***/
				if( last_x < start_x )
				{
					/*** swap values ***/
					int tmp = last_x;
					last_x = start_x;
					start_x = tmp;
				}
				
				/*** Is the second y above of the first? ***/
				if( last_y < start_y )
				{
					/*** swap values ***/
					int tmp = last_y;
					last_y = start_y;
					start_y = tmp;
				}
				
				int width = last_x - start_x;
				int height = last_y - start_y;
				
				newObject = new UMLObject().move(start_x, start_y, 1);
				
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
