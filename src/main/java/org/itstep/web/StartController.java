package org.itstep.web;

import javax.servlet.http.HttpSession;

import org.itstep.domain.Role;
import org.itstep.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StartController {
	@RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
	public String index(HttpSession session) {
		if(session != null) {
			User user = (User)session.getAttribute("sessionUser");
			if(user != null && user.getRole() != null) {
				switch(user.getRole()) {
					case ADMIN: return "redirect:/login.html";
					case CLIENT: return "redirect:/login.html";
					case COURIER: return "redirect:/login.html";
					case MANAGER: return "redirect:/product/list.html";
					default: throw new EnumConstantNotPresentException(Role.class, user.getRole().toString());
				}
			}
		}
		return "redirect:/login.html";
	}
}
