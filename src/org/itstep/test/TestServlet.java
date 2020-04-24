package org.itstep.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		pw.println("<p style=\"color: green\">START SERVLET</p>");
		pw.println("<p>URLs:</p>");
		pw.println("<ul>");
		pw.println("<li><a href=\"product/list.html\">Products list</a></li>");
		pw.println("</ul>");
	}
}
