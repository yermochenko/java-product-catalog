package org.itstep.ui;

import org.itstep.domain.Product;
import org.itstep.logic.LogicException;
import org.itstep.util.List;

public class ProductListCommand extends ProductCommand {
	@Override
	public void exec(String[] args) throws LogicException {
		List<Product> products = getProductService().findAll();
		if(products.size() > 0) {
			for(int i = 0; i < products.size(); i++) {
				System.out.println(products.get(i));
			}
		} else {
			System.out.println("Список товаров пуст");
		}
	}
}
