package org.itstep.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.itstep.domain.User;

public class AuthorizationLogger implements HttpSessionListener, HttpSessionAttributeListener {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");

	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		User user = (User)e.getSession().getAttribute("sessionUser");
		if(user != null) {
			System.out.printf("[%s] User \"%s\" (%s) logout\n", DATE_FORMAT.format(new Date()), user.getLogin(), user.getRole());
		}
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent e) {
		if("sessionUser".equals(e.getName())) {
			User user = (User)e.getValue();
			System.out.printf("[%s] User \"%s\" (%s) login\n", DATE_FORMAT.format(new Date()), user.getLogin(), user.getRole());
		}
	}
}
