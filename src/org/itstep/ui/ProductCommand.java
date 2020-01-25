package org.itstep.ui;

import org.itstep.logic.ProductService;

abstract public class ProductCommand implements Command {
	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	protected ProductService getProductService() {
		return productService;
	}
}
