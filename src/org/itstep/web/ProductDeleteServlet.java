package org.itstep.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.Factory;
import org.itstep.logic.LogicException;
import org.itstep.logic.ProductService;

public class ProductDeleteServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idsString[] = req.getParameterValues("id");
		if(idsString != null) {
			try {
				List<Long> ids = new ArrayList<>(idsString.length);
				for(String id : idsString) {
					ids.add(Long.parseLong(id));
				}
				try(Factory factory = new Factory()) {
					ProductService service = factory.getProductService();
					service.delete(ids);
				} catch(LogicException e) {
					throw new ServletException(e);
				}
			} catch(NumberFormatException e) {
				resp.sendError(400);
				return;
			}
		}
		resp.sendRedirect(req.getContextPath() + "/product/list.html");
	}
}
