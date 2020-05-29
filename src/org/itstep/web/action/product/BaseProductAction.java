package org.itstep.web.action.product;

import org.itstep.logic.ProductService;
import org.itstep.web.action.Action;

public abstract class BaseProductAction implements Action {
	private ProductService productService;

	protected ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
}
