package org.itstep.web.action.product;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.domain.Category;
import org.itstep.domain.Product;
import org.itstep.logic.CategoryService;
import org.itstep.logic.LogicException;

public class ProductListAction extends BaseProductAction {
	private CategoryService categoryService;

	private CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		List<Category> categories = getCategoryService().findAll();
		req.setAttribute("categories", categories);
		Map<Category, List<Product>> products = getProductService().findLatest();
		req.setAttribute("products", products);
		return null;
	}
}
