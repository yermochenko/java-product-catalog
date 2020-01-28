package org.itstep.ui;

import org.itstep.domain.Product;

public class ProductSaveCommand extends ProductCommand {
	@Override
	public void exec(String[] args) {
		if(args.length == 4 || args.length == 5) {
			Product product = new Product();
			int offset = 0;
			if(args.length == 5) {
				product.setId(Long.valueOf(args[0]));
				offset = 1;
			}
			product.setCategory(args[offset]);
			product.setName(args[1 + offset]);
			product.setPrice(Long.valueOf(args[2 + offset]));
			product.setAmount(Integer.valueOf(args[3 + offset]));
			getProductService().save(product);
			System.out.println("Данные успешно сохранены");
		} else {
			System.out.println("Неверное количество аргументов");
		}
	}
}
