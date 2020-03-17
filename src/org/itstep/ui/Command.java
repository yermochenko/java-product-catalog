package org.itstep.ui;

import org.itstep.logic.LogicException;

public interface Command {
	boolean exec(String args[]) throws LogicException;
}
