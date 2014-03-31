package edu.millersville.cs.segfault.model;

import java.util.Comparator;

/**************************************************************************
 * A comparator which compares drawables by their z value.
 */
public class ZComparator implements Comparator<DrawableUML> {

	public int compare(DrawableUML a, DrawableUML b) {
		if (a.getZ() > b.getZ())
		{
			return 1;
		} else if (a.getZ() == b.getZ()) {
			return 0;
		}
		return -1;
	}
	
}