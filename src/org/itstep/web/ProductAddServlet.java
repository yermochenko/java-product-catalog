package org.itstep.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.Factory;
import org.itstep.domain.Category;
import org.itstep.logic.CategoryService;
import org.itstep.logic.LogicException;

public class ProductAddServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(Factory factory = new Factory()) {
			CategoryService service = factory.getCategoryService();
			List<Category> categories = service.findAll();
			req.setAttribute("categories", categories);
			req.getRequestDispatcher("/WEB-INF/jsp/product/add.jsp").forward(req, resp);
		} catch(LogicException e) {
			throw new ServletException(e);
		}
	}
}
