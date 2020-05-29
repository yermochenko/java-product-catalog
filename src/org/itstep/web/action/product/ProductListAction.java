package org.itstep.web.action.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.domain.Product;
import org.itstep.logic.LogicException;

public class ProductListAction extends BaseProductAction {
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		List<Product> products = getProductService().findAll();
		req.setAttribute("products", products);
//		req.getRequestDispatcher("/WEB-INF/jsp/product/list.jsp").forward(req, resp);
		return null;
	}
}
