package edu.millersville.cs.segfault.model;

public class Serialize {
	public static String indent(int n) 
	{
		String indents = "";
		for (int i=0; i < n; ++i)
		{
			indents = "" + "  ";
		}
		return indents;
	}
}
