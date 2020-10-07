package org.itstep.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itstep.domain.User;
import org.itstep.logic.LogicException;
import org.itstep.logic.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Scope("prototype")
public class AuthorizationController implements HttpSessionListener, HttpSessionAttributeListener {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public String login(HttpServletRequest req, RedirectAttributes redirectAttributes) throws LogicException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		if(login == null && password == null) {
			return "/login";
		}
		if(login == null || login.isBlank() || password == null) {
			throw new BadRequestException();
		}
		User user = userService.authenticate(login, password);
		if(user != null) {
			HttpSession session = req.getSession();
			session.setAttribute("sessionUser", user);
			return "redirect:/index.html";
		} else {
			redirectAttributes.addAttribute("message", "Неверное имя пользователя или пароль");
			return "redirect:/login.html";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/index.html";
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent e) {
		if("sessionUser".equals(e.getName())) {
			User user = (User)e.getValue();
			logger.info("Authorization: user \"{}\" ({}) login", user.getLogin(), user.getRole());
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		User user = (User)e.getSession().getAttribute("sessionUser");
		if(user != null) {
			logger.info("Authorization: user \"{}\" ({}) logout", user.getLogin(), user.getRole());
		}
	}
}
