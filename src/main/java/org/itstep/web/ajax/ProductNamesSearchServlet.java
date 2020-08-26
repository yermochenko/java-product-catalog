package org.itstep.web.ajax;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.domain.Product;
import org.itstep.logic.LogicException;
import org.itstep.logic.ProductService;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductNamesSearchServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String search = req.getQueryString();
		if(search != null) {
			search = URLDecoder.decode(search, "UTF-8");
			try {
				ApplicationContext context = (ApplicationContext)getServletContext().getAttribute("spring-context");
				ProductService service = context.getBean(ProductService.class);
				List<Product> products = service.findNamesBySearchString(search);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.writeValue(resp.getOutputStream(), products);
			} catch(LogicException e) {
				resp.sendError(500);
			}
		} else {
			resp.sendError(400);
		}
	}
}
