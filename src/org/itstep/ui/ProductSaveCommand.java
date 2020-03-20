package org.itstep.ui;

import java.text.ParseException;

import org.itstep.domain.Category;
import org.itstep.domain.Product;
import org.itstep.logic.LogicException;

public class ProductSaveCommand extends ProductCommand {
	@Override
	public boolean exec(String[] args) throws LogicException {
		if(args.length == 5 || args.length == 6) {
			try {
				Product product = new Product();
				int offset = 0;
				if(args.length == 6) {
					product.setId(Long.valueOf(args[0]));
					offset = 1;
				}
				try {
					Long categoryId = Long.valueOf(args[offset]);
					product.setCategory(new Category());
					product.getCategory().setId(categoryId);
					product.setName(args[1 + offset]);
					try {
						product.setPrice(Long.valueOf(args[2 + offset]));
						if(product.getPrice() <= 0) {
							throw new NumberFormatException();
						}
						try {
							product.setAmount(Integer.valueOf(args[3 + offset]));
							if(product.getAmount() < 0) {
								throw new NumberFormatException();
							}
							try {
								product.setDate(Product.FORMAT.parse(args[4 + offset]));
								getProductService().save(product);
								System.out.println("Данные успешно сохранены (ID = " + product.getId() + ")");
							} catch(ParseException e) {
								System.out.println("Дата " + args[4 + offset] + " должна соответствовать формату " + Product.FORMAT.toPattern());
							}
						} catch(NumberFormatException e) {
							System.out.println("Количество " + args[3 + offset] + " должно быть целым неотрицательным числом");
						}
					} catch(NumberFormatException e) {
						System.out.println("Цена " + args[2 + offset] + " должна быть целым положительным числом");
					}
				} catch(NumberFormatException e) {
					System.out.println("Идентификатор категории " + args[offset] + " должен быть целым числом");
				}
			} catch(NumberFormatException e) {
				System.out.println("Идентификатор " + args[0] + " должен быть целым числом");
			}
		} else {
			System.out.println("Неверное количество аргументов");
		}
		return true;
	}
}
