import java.util.Scanner;

import org.itstep.domain.Product;
import org.itstep.ui.CommandManager;
import org.itstep.util.List;

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
