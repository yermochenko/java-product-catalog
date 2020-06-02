package org.itstep.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.Factory;
import org.itstep.logic.LogicException;
import org.itstep.web.action.Action;
import org.itstep.web.action.Action.Result;
import org.itstep.web.action.Action.ResultType;
import org.itstep.web.action.ActionException;

public class DispatcherServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		uri = uri.substring(req.getContextPath().length());
		if(uri.endsWith(".html")) {
			uri = uri.substring(0, uri.length() - ".html".length());
		}
		try(Factory factory = new Factory()) {
			Action action = factory.getAction(uri);
			Result result = null;
			if(action != null) {
				result = action.exec(req, resp);
			}
			if(result == null || result.getType() == ResultType.FORWARD) {
				if(result != null) {
					uri = result.getUrl();
				}
				req.getRequestDispatcher("/WEB-INF/jsp" + uri + ".jsp").forward(req, resp);
			} else {
				uri = req.getContextPath() + result.getUrl() + ".html";
				String params = result.getParameters();
				if(!params.isEmpty()) {
					uri += "?" + params;
				}
				resp.sendRedirect(uri);
			}
		} catch(ActionException e) {
			resp.sendError(e.getCode());
		} catch(LogicException e) {
			throw new ServletException(e);
		}
	}
}
