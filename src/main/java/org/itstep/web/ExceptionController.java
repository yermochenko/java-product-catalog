package org.itstep.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itstep.logic.LogicException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionController {
	private static final Logger logger = LogManager.getLogger();

	@ExceptionHandler(LogicException.class)
	public String error(Model model, HttpServletRequest req, LogicException e) {
		logger.error("Error processing request on URI \"{}\" from client {}", req.getRequestURI(), req.getLocalAddr());
		model.addAttribute("exception", e);
		return "error";
	}
}
