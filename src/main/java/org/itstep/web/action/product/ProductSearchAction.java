package org.itstep.web.action.product;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.domain.Category;
import org.itstep.domain.Product;
import org.itstep.logic.CategoryService;
import org.itstep.logic.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ProductSearchAction extends BaseProductAction {
	@Autowired
	private CategoryService categoryService;

	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		String query = req.getParameter("query");
		if(query != null && query.length() >= 3) {
			List<Category> categories = categoryService.findAll();
			req.setAttribute("categories", categories);
			Map<Category, List<Product>> products = productService.findBySearchString(query);
			req.setAttribute("products", products);
			return new Result("/general/products", ResultType.FORWARD);
		}
		return new Result("/product/list");
	}
}
