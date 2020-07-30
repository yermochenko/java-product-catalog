package org.itstep.web.action.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.domain.Category;
import org.itstep.domain.Product;
import org.itstep.logic.CategoryService;
import org.itstep.logic.LogicException;
import org.itstep.web.action.ActionException;

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
		String categoryId = req.getParameter("category");
		if(categoryId != null) {
			try {
				Category category = getCategoryService().findById(Long.parseLong(categoryId));
				if(category == null) {
					throw new IllegalArgumentException();
				}
				Map<Category, List<Product>> products = new HashMap<>();
				products.put(category, getProductService().findByCategory(category.getId()));
				req.setAttribute("products", products);
			} catch(IllegalArgumentException e) {
				throw new ActionException(e, 400);
			}
		} else {
			Map<Category, List<Product>> products = getProductService().findLatest();
			req.setAttribute("products", products);
		}
		return null;
	}
}
