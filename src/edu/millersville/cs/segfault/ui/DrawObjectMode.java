package edu.millersville.cs.segfault.ui;

import java.awt.Graphics;

import edu.millersville.cs.segfault.model.UMLObject;

public class DrawObjectMode implements PanelInteractionMode 
{

	boolean engaged;
	int start_x;
	int start_y;
	UMLPanel caller;
	
	public DrawObjectMode(UMLPanel caller)
	{
		engaged = false;
		this.caller = caller;
	}
	
	@Override
	public void left_click(int x, int y) {
		if (engaged)
		{
			int width = x - start_x;
			int height = y - start_y;
			try {
				UMLObject newObject = new UMLObject().move(start_x, start_y, 0);
				newObject = newObject.resize(width, height);
				caller.changeModel(caller.model().add(newObject));
			} catch (Exception e) {
				System.out.print("Failed to add object to model!");
			}
		} else {
			start_x = x;
			start_y = y;
		}
	}

	@Override
	public void draw(Graphics g, int mouseX, int mouseY) {
		if (engaged)
		{
			g.drawRect(start_x, start_y, mouseX, mouseY);
		}

	}

	@Override
	public void right_click(int x, int y) {
		
	}
}
