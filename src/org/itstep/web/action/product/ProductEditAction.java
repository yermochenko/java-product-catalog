package org.itstep.web.action.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.domain.Category;
import org.itstep.domain.Product;
import org.itstep.logic.CategoryService;
import org.itstep.logic.LogicException;
import org.itstep.web.action.ActionException;

public class ProductEditAction extends BaseProductAction {
	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		try {
			List<Category> categories = categoryService.findAll();
			req.setAttribute("categories", categories);
			String id = req.getParameter("id");
			if(id != null) {
				Product product = getProductService().findById(Long.parseLong(id));
				if(product == null) {
					throw new IllegalArgumentException();
				}
				req.setAttribute("product", product);
			}
			return null;
		} catch(IllegalArgumentException e) {
			throw new ActionException(e, 404);
		}
	}
}
