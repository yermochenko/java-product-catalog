import java.util.Scanner;

import org.itstep.logic.LogicException;
import org.itstep.ui.CommandManager;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException {
		try(Scanner scanner = new Scanner(System.in)) {
			Class.forName("org.postgresql.Driver");
			try(CommandManager manager = new CommandManager()) {
				boolean goOn = true;
				while(goOn) {
					System.out.print("> ");
					String command = scanner.nextLine();
					goOn = manager.exec(command);
				}
			} catch(LogicException e) {
				System.out.println("Ошибка доступа к базе данных");
			}
		} catch(ClassNotFoundException e) {
			System.out.println("Ошибка запуска приложения");
		}
	}
}
