package org.itstep.web.action.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.domain.Category;
import org.itstep.domain.Product;
import org.itstep.logic.CategoryService;
import org.itstep.logic.LogicException;
import org.itstep.web.action.ActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ProductEditAction extends BaseProductAction {
	@Autowired
	private CategoryService categoryService;

	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		try {
			List<Category> categories = categoryService.findAll();
			req.setAttribute("categories", categories);
			String id = req.getParameter("id");
			if(id != null) {
				Product product = productService.findById(Long.parseLong(id));
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
