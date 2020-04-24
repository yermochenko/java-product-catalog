package org.itstep.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.domain.Product;

public class ProductListViewServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Product> products = (List<Product>)req.getAttribute("products");
		int size = products.size();
		resp.setCharacterEncoding("UTF-8");
		PrintWriter pw = resp.getWriter();
		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta charset=\"UTF-8\">");
		pw.println("<title>Products list</title>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1 style=\"color: green\">Products list</h1>");
		pw.println("<table border=\"1\">");
		pw.println("<tr>");
		pw.println("<th>Категория</th>");
		pw.println("<th>Название</th>");
		pw.println("<th>Цена</th>");
		pw.println("<th>Количество</th>");
		pw.println("<th>Дата добавления</th>");
		pw.println("</tr>");
		for(Product product : products) {
			pw.println("<tr>");
			pw.printf("<td>%s</td>", product.getCategory().getName());
			pw.printf("<td>%s</td>", product.getName());
			pw.printf("<td>$%d.%02d</td>", product.getPrice() / 100, product.getPrice() % 100);
			pw.printf("<td>%d шт.</td>", product.getAmount());
			pw.printf("<td>%1$td.%1$tm.%1$tY, %1$tA</td>", product.getDate());
			pw.println("</tr>");
		}
		pw.println("</table>");
		pw.printf("<p>Итого %d товаров</p>", size);
		pw.println("</body>");
		pw.println("</html>");
	}
}
