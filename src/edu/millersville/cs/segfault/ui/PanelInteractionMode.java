package edu.millersville.cs.segfault.ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PanelInteractionMode implements MouseListener, MouseMotionListener, KeyListener {
	
	private boolean controlDown;
	private boolean shiftDown;

	
	void draw(Graphics g) {
		
	}
	
	void leaveMode() {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_CONTROL: controlDown=true;
								  break;
		case KeyEvent.VK_SHIFT:   shiftDown=true;
								  break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_CONTROL: controlDown=false;
								  break;
		case KeyEvent.VK_SHIFT:   shiftDown=false;
								  break;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	public boolean isControlDown() {
		return controlDown;
	}
	
	public boolean isShiftDown() {
		return shiftDown;
	}
}
