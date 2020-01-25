package org.itstep.ui;

public class ExitCommand implements Command {
	@Override
	public void exec(String[] args) {
		System.out.println("Всего доброго");
		System.exit(0);
	}
}
