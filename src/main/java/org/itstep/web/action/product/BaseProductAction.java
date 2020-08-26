package org.itstep.web.action.product;

import org.itstep.logic.ProductService;
import org.itstep.web.action.Action;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseProductAction implements Action {
	@Autowired
	protected ProductService productService;
}
