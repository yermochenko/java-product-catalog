package org.itstep.ui;

public class ExitCommand implements Command {
	@Override
	public boolean exec(String[] args) {
		System.out.println("Всего доброго");
		return false;
	}
}
