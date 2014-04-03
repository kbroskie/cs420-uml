package edu.millersville.cs.segfault.ui.console;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JTextField;

class ConsoleEngine implements ActionListener {

	ScriptEngine engine;
	JTextField input;
	
	public ConsoleEngine(JTextField input, Console c) {
		ScriptEngineManager factory = new ScriptEngineManager();
		this.engine = factory.getEngineByName("JavaScript");
		engine.put("console", c);
		this.input = input;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		try {
			engine.eval(input.getText());
		} catch (ScriptException ex) {
			
		} finally {
			input.setText("");
		}
		
	}
	
}
