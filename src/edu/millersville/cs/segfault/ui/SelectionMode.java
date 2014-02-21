package edu.millersville.cs.segfault.ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import edu.millersville.cs.segfault.model.DrawableUML;
import edu.millersville.cs.segfault.model.UMLObject;
import edu.millersville.cs.segfault.model.UMLRelation;

public class SelectionMode implements PanelInteractionMode {

	private DrawableUML target;
	private UMLPanel caller;
	private boolean engaged;
	
	public SelectionMode(UMLPanel caller)
	{
		this.caller = caller;
		this.engaged = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		caller.requestFocusInWindow();
		boolean onRelation = false;
		Iterator<UMLRelation> rIter = caller.model().relationIterator();
		while (rIter.hasNext())
		{
			UMLRelation current = rIter.next();
			if (current.near(e.getX(), e.getY(), 10))
			{
				onRelation = true;
				changeSelection(current);
			}
		}
		if (!onRelation) {
			boolean onObject = false;
			Iterator<UMLObject> oIter = caller.model().objectIterator();
			while (oIter.hasNext())
			{
				UMLObject current = oIter.next();
				if (current.within(e.getX(), e.getY()))
				{
					onObject = true;
					changeSelection(current);
				}
			}
			if (!onObject)
			{
				noSelection();
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
	public void draw(Graphics g) 
	{

	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_DELETE)
		{
			if (engaged)
			{
				caller.changeModel(caller.model().remove(target));
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	private void changeSelection(DrawableUML target)
	{
		if (engaged)
		{
			this.target.unselect();
		}
		this.engaged = true;
		target.select();
		this.target = target;
		caller.repaint();
	}
	
	private void noSelection()
	{
		if (engaged)
		{
			this.target.unselect();
			this.engaged = false;
		}
		caller.repaint();
		
	}
	
}
