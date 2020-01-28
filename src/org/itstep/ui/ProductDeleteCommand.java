package org.itstep.ui;

public class ProductDeleteCommand extends ProductCommand {
	@Override
	public void exec(String[] args) {
		if(args.length == 1) {
			Long id = Long.valueOf(args[0]);
			getProductService().delete(id);
			System.out.println("Товар успешно удалён");
		} else {
			System.out.println("Неверное количество аргументов");
		}
	}
}
