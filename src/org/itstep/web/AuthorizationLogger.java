package org.itstep.web;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itstep.domain.User;

public class AuthorizationLogger implements HttpSessionListener, HttpSessionAttributeListener {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		User user = (User)e.getSession().getAttribute("sessionUser");
		if(user != null) {
			logger.info(String.format("User \"%s\" (%s) logout", user.getLogin(), user.getRole()));
		}
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent e) {
		if("sessionUser".equals(e.getName())) {
			User user = (User)e.getValue();
			logger.info(String.format("User \"%s\" (%s) login", user.getLogin(), user.getRole()));
		}
	}
}
