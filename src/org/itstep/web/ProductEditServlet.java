package org.itstep.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.Factory;
import org.itstep.domain.Category;
import org.itstep.domain.Product;
import org.itstep.logic.CategoryService;
import org.itstep.logic.LogicException;
import org.itstep.logic.ProductService;

public class ProductEditServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(Factory factory = new Factory()) {
			CategoryService categoryService = factory.getCategoryService();
			List<Category> categories = categoryService.findAll();
			req.setAttribute("categories", categories);
			String id = req.getParameter("id");
			if(id != null) {
				ProductService productService = factory.getProductService();
				Product product = productService.findById(Long.parseLong(id));
				if(product == null) {
					throw new IllegalArgumentException();
				}
				req.setAttribute("product", product);
			}
			req.getRequestDispatcher("/WEB-INF/jsp/product/edit.jsp").forward(req, resp);
		} catch(LogicException e) {
			throw new ServletException(e);
		} catch(IllegalArgumentException e) {
			resp.sendError(404);
		}
	}
}
