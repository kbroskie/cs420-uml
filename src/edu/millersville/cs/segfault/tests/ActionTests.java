package edu.millersville.cs.segfault.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.millersville.cs.segfault.ui.UMLWindow;
import edu.millersville.cs.segfault.ui.menu.ActionType;
import edu.millersville.cs.segfault.ui.menu.actions.Delete;
import edu.millersville.cs.segfault.ui.menu.actions.Exit;
import edu.millersville.cs.segfault.ui.menu.actions.New;
import edu.millersville.cs.segfault.ui.menu.actions.Open;
import edu.millersville.cs.segfault.ui.menu.actions.Redo;
import edu.millersville.cs.segfault.ui.menu.actions.Save;
import edu.millersville.cs.segfault.ui.menu.actions.SaveAs;
import edu.millersville.cs.segfault.ui.menu.actions.Select;
import edu.millersville.cs.segfault.ui.menu.actions.Undo;

//******************************************************************************************//
// Action Tests
//
// Tests the ui menu actions classes.
//
// Author: Benjamin Nothstein
//******************************************************************************************//

public class ActionTests {

	@Test
	public void test() {
		
		UMLWindow testWindow = new UMLWindow();
		
		//**********************************************************************************//
		// Delete Tests
		//**********************************************************************************//
		
		Delete testDelete = new Delete(testWindow);
		assertTrue(testDelete.getType() == ActionType.DELETE);
		testDelete.actionPerformed(null);
		
		//**********************************************************************************//
		// Exit Tests
		//**********************************************************************************//
		
		Exit testExit = new Exit();
		assertTrue(testExit.getType() == ActionType.EXIT);
		//testExit.actionPerformed(null);
		
		//**********************************************************************************//
		// New Tests
		//**********************************************************************************//
		
		New testNew = new New(testWindow);
		assertTrue(testNew.getType() == ActionType.NEW);
		testNew.actionPerformed(null);
		
		//**********************************************************************************//
		// Open Tests
		//**********************************************************************************//
		
		Open testOpen = new Open(testWindow);
		assertTrue(testOpen.getType() == ActionType.OPEN);
		testOpen.actionPerformed(null);
		
		//**********************************************************************************//
		// Redo Tests
		//**********************************************************************************//
		
		Redo testRedo = new Redo(testWindow);
		assertTrue(testRedo.getType() == ActionType.REDO);
		testRedo.actionPerformed(null);
		
		//**********************************************************************************//
		// Save Tests
		//**********************************************************************************//
		
		Save testSave = new Save(testWindow);
		assertTrue(testSave.getType() == ActionType.SAVE);
		testSave.actionPerformed(null);
		
		//**********************************************************************************//
		// SaveAs Tests
		//**********************************************************************************//
		
		SaveAs testSaveAs = new SaveAs(testWindow);
		assertTrue(testSaveAs.getType() == ActionType.SAVE_AS);
		testSaveAs.actionPerformed(null);
		
		//**********************************************************************************//
		// Select Tests
		//**********************************************************************************//
		
		Select testSelect = new Select(testWindow);
		assertTrue(testSelect.getType() == ActionType.SELECT);
		testSelect.actionPerformed(null);
		
		//**********************************************************************************//
		// Undo Tests
		//**********************************************************************************//
		
		Undo testUndo = new Undo(testWindow);
		assertTrue(testUndo.getType() == ActionType.UNDO);
		testUndo.actionPerformed(null);
		
		//**********************************************************************************//
		
	}

}
