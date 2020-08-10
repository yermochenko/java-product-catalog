package org.itstep.web.action.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.domain.Category;
import org.itstep.domain.Product;
import org.itstep.logic.LogicException;
import org.itstep.web.action.ActionException;

public class ProductSaveAction extends BaseProductAction {
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		try {
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			if(name == null || name.isBlank()) {
				throw new IllegalArgumentException();
			}
			String category = req.getParameter("category");
			String price = req.getParameter("price");
			String amount = req.getParameter("amount");
			Product product = new Product();
			if(id != null) {
				product.setId(Long.parseLong(id));
			}
			product.setName(name);
			product.setCategory(new Category());
			product.getCategory().setId(Long.parseLong(category));
			product.setPrice(Long.parseLong(price));
			if(product.getPrice() <= 0) {
				throw new IllegalArgumentException();
			}
			product.setAmount(Integer.parseInt(amount));
			if(product.getAmount() <= 0) {
				throw new IllegalArgumentException();
			}
			getProductService().save(product);
			return new Result("/product/list");
		} catch(IllegalArgumentException e) {
			throw new ActionException(e, 400);
		}
	}
}
