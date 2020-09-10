package org.itstep.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.itstep.web.action.product.ProductDeleteAction;
import org.itstep.web.action.product.ProductEditAction;
import org.itstep.web.action.product.ProductListAction;
import org.itstep.web.action.product.ProductSaveAction;
import org.itstep.web.action.product.ProductSearchAction;
import org.springframework.context.ApplicationContext;

public class ActionFactory {
	private static Map<String, Class<? extends Action>> actions = new HashMap<>();
	static {
		actions.put("/", MainAction.class);
		actions.put("/index", MainAction.class);
		actions.put("/login", LoginAction.class);
		actions.put("/logout", LogoutAction.class);
		actions.put("/product/list", ProductListAction.class);
		actions.put("/product/search", ProductSearchAction.class);
		actions.put("/product/edit", ProductEditAction.class);
		actions.put("/product/save", ProductSaveAction.class);
		actions.put("/product/delete", ProductDeleteAction.class);
	}

	private static ApplicationContext context;

	public static void init(ServletContext context) {
		ActionFactory.context = (ApplicationContext)context.getAttribute("spring-context");
	}

	public static Action getAction(String url) {
		Class<?> actionClass = actions.get(url);
		if(actionClass != null) {
			return (Action)context.getBean(actionClass);
		} else {
			return null;
		}
	}
}
