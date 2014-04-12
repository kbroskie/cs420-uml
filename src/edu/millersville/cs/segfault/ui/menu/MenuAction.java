package edu.millersville.cs.segfault.ui.menu;

import edu.millersville.cs.segfault.ui.Toolbar;

/*****************************************************************************
 * An interface for actions which can be inserted into a {@link MenuBar}     *
 * or {@link Toolbar}                                           			 *
 * @author Kimberlyn Broskie                                                  *
 *****************************************************************************/
public interface MenuAction {
	ActionType getType();
}
