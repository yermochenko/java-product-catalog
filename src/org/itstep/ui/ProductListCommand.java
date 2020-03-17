package org.itstep.ui;

import java.util.List;

import org.itstep.domain.Product;
import org.itstep.logic.LogicException;

public class ProductListCommand extends ProductCommand {
	@Override
	public boolean exec(String[] args) throws LogicException {
		List<Product> products = getProductService().findAll();
		if(products.size() > 0) {
			for(Product product : products) {
				System.out.println(product);
			}
		} else {
			System.out.println("Список товаров пуст");
		}
		return true;
	}
}
