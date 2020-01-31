package org.itstep.ui;

import org.itstep.logic.LogicException;

public interface Command {
	void exec(String args[]) throws LogicException;
}
