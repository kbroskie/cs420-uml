package edu.millersville.cs.segfault.ui.console;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Console extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTextArea text;
	private JTextField input;
	private ConsoleEngine engine;
	
	public Console() {
		super();
		
		
		this.setLayout(new BorderLayout());
		
		text = new JTextArea(10,80);
		this.add(text, BorderLayout.CENTER);
		
		input = new JTextField();
		engine = new ConsoleEngine(input, this);
		input.addActionListener(this.engine);
		this.add(input, BorderLayout.SOUTH);
	}
	
	public void log(String s) {
		text.append(s + "\n");
	}
	
	
}


