package org.itstep.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.Factory;
import org.itstep.domain.Category;
import org.itstep.domain.Product;
import org.itstep.logic.LogicException;
import org.itstep.logic.ProductService;

public class ProductSaveServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String name = req.getParameter("name");
			if(name == null || name.isBlank()) {
				throw new IllegalArgumentException();
			}
			String category = req.getParameter("category");
			String price = req.getParameter("price");
			String amount = req.getParameter("amount");
			Product product = new Product();
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
			try(Factory factory = new Factory()) {
				ProductService service = factory.getProductService();
				service.save(product);
				req.getRequestDispatcher("/product/list.html").forward(req, resp);
			} catch(LogicException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			throw new ServletException(e);
		}
	}
}
