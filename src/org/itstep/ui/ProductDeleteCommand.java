package org.itstep.ui;

import org.itstep.logic.LogicException;

public class ProductDeleteCommand extends ProductCommand {
	@Override
	public void exec(String[] args) throws LogicException {
		if(args.length == 1) {
			Long id = Long.valueOf(args[0]);
			getProductService().delete(id);
			System.out.println("Товар успешно удалён");
		} else {
			System.out.println("Неверное количество аргументов");
		}
	}
}
