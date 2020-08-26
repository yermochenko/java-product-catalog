package org.itstep.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.itstep.domain.Role;
import org.itstep.domain.User;
import org.itstep.logic.LogicException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MainAction implements Action {
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		HttpSession session = req.getSession(false);
		if(session != null) {
			User user = (User)session.getAttribute("sessionUser");
			if(user != null) {
				switch(user.getRole()) {
					case ADMIN: return new Result("/login");
					case CLIENT: return new Result("/login");
					case COURIER: return new Result("/login");
					case MANAGER: return new Result("/product/list");
					default: throw new EnumConstantNotPresentException(Role.class, user.getRole().toString());
				}
			}
		}
		return new Result("/login");
	}
}
