package org.itstep.web.ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.Factory;
import org.itstep.domain.Product;
import org.itstep.logic.LogicException;
import org.itstep.logic.ProductService;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductListServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(Factory factory = new Factory()) {
			ProductService service = factory.getProductService();
			List<Product> products = service.findAll();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(resp.getOutputStream(), products);
		} catch(LogicException e) {
			resp.sendError(500);
		}
	}
}
