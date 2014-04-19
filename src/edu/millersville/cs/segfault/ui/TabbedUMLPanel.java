package edu.millersville.cs.segfault.ui;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class TabbedUMLPanel extends JTabbedPane {


	private static final long serialVersionUID = 4153866745992934149L;

	private final int Virtual_Key_0 = 48;
	//private JScrollPane scrollableUMLPanel;
	
	private final UMLWindow parent;


	public TabbedUMLPanel (UMLWindow parent) {
		super();	

		this.parent = parent;
		
		//String title = "Tab 1";
		//getNewTab(1, title);
		//setMnemonicAt(0, KeyEvent.VK_1);
		addTab("Tab 1", new UMLPanel(parent));

		Component newTab = getComponentAt(0);
		JScrollPane scrollpane = new JScrollPane(newTab);
		scrollpane.setViewportView(newTab);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		//addTab("Tab 2", new UMLPanel());
		//Component newTab1 = getComponentAt(2);
		//JScrollPane scrollpane1 = new JScrollPane(newTab1);
		//scrollpane1.setViewportView(newTab1);
		//scrollpane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollpane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//JScrollPane scrollpane1 = new JScrollPane(getNewTab(1, "Tab 2"));

		
		//int tabNumber = 1;

		//char keyCode = (char) (Virtual_Key_0 + tabNumber);
		

		//setMnemonicAt(0, KeyEvent.getExtendedKeyCodeForChar(keyCode));
		//setMnemonicAt(1, KeyEvent.getExtendedKeyCodeForChar(keyCode + 1));
		add(scrollpane);
	//	add(scrollpane1);
	
		// Set the options for the scroll panel.
			
		
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
	
	public Component getNewTab(int tabNumber, String title) {
		addTab(title, new UMLPanel(parent));
		return getComponentAt(tabNumber);
	}
}
