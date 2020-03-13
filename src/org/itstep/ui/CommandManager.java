package org.itstep.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import org.itstep.Factory;
import org.itstep.logic.LogicException;

public class CommandManager implements Command {
	private Factory factory = new Factory();
	private Map<String, Command> commands = new LinkedHashMap<>();

	public CommandManager() {
		commands.put("help", this);
		commands.put("list", factory.getProductListCommand());
		commands.put("save", factory.getProductSaveCommand());
		commands.put("delete", factory.getProductDeleteCommand());
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
				try {
					command.exec(args);
				} catch(LogicException e) {
					System.out.println("Команда не может быть выполнена");
				}
			} else {
				System.out.println("Неизвестная команда");
			}
		}
	}

	@Override
	public void exec(String[] args) {
		System.out.println("Доступны следующие команды:");
		for(String command : commands.keySet()) {
			System.out.println("\t" + command);
		}
	}
}
