package org.itstep.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.itstep.Factory;
import org.itstep.domain.User;
import org.itstep.logic.LogicException;
import org.itstep.logic.UserService;

public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String login = req.getParameter("login");
			String password = req.getParameter("password");
			if(login == null || login.isBlank() || password == null) {
				throw new IllegalArgumentException();
			}
			try(Factory factory = new Factory()) {
				UserService service = factory.getUserService();
				User user = service.authenticate(login, password);
				if(user != null) {
					HttpSession session = req.getSession();
					session.setAttribute("sessionUser", user);
					resp.sendRedirect(req.getContextPath() + "/index.html");
				} else {
					resp.sendRedirect(req.getContextPath() + "/login.html?message=" + URLEncoder.encode("Неверное имя пользователя или пароль", "UTF-8"));
				}
			} catch(LogicException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			resp.sendError(400);
		}
	}
}
