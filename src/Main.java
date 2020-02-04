import java.util.Scanner;

import org.itstep.ui.CommandManager;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		CommandManager manager = new CommandManager();
		while(true) {
			System.out.print("> ");
			String command = scanner.nextLine();
			manager.exec(command);
		}
	}
}
