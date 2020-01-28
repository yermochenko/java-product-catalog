package org.itstep.ui;

import org.itstep.Factory;
import org.itstep.util.List;
import org.itstep.util.Map;

public class CommandManager implements Command {
	private Factory factory = new Factory();
	private Map<String, Command> commands = new Map<>();

	public CommandManager() {
		commands.put("help", this);
		commands.put("list", factory.getProductListCommand());
		commands.put("save", factory.getProductSaveCommand());
		commands.put("exit", new ExitCommand());
	}

	public void exec(String commandLine) {
		String pair[] = commandLine.split(" ");
		if(pair.length > 0) {
			Command command = commands.get(pair[0]);
			if(command != null) {
				String args[];
				if(pair.length > 1) {
					args = pair[1].split(";");
				} else {
					args = new String[] {};
				}
				command.exec(args);
			} else {
				System.out.println("Неизвестная команда");
			}
		}
	}

	@Override
	public void exec(String[] args) {
		System.out.println("Доступны следующие команды:");
		List<String> commands = this.commands.getKeys();
		for(int i = 1; i < commands.size(); i++) {
			System.out.println("\t" + commands.get(i));
		}
	}
}
