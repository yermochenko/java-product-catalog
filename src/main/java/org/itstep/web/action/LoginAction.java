package org.itstep.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.itstep.domain.User;
import org.itstep.logic.LogicException;
import org.itstep.logic.UserService;

public class LoginAction implements Action {
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		if(login == null && password == null) {
			return null;
		}
		try {
			if(login == null || login.isBlank() || password == null) {
				throw new IllegalArgumentException();
			}
			User user = userService.authenticate(login, password);
			if(user != null) {
				HttpSession session = req.getSession();
				session.setAttribute("sessionUser", user);
				return new Result("/index");
			} else {
				return new Result("/login").add("message", "Неверное имя пользователя или пароль");
			}
		} catch(IllegalArgumentException e) {
			throw new ActionException(e, 400);
		}
	}
}
