package org.itstep.util.ioc;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.itstep.logic.CategoryService;
import org.itstep.logic.CategoryServiceImpl;
import org.itstep.logic.LogicException;
import org.itstep.logic.ProductService;
import org.itstep.logic.ProductServiceImpl;
import org.itstep.logic.UserService;
import org.itstep.logic.UserServiceImpl;
import org.itstep.storage.CategoryDao;
import org.itstep.storage.ProductDao;
import org.itstep.storage.UserDao;
import org.itstep.storage.postgres.CategoryDbDaoImpl;
import org.itstep.storage.postgres.ProductDbDaoImpl;
import org.itstep.storage.postgres.UserDbDaoImpl;
import org.itstep.util.pool.ConnectionPool;
import org.itstep.util.pool.ConnectionPoolException;
import org.itstep.web.action.Action;
import org.itstep.web.action.LoginAction;
import org.itstep.web.action.LogoutAction;
import org.itstep.web.action.MainAction;
import org.itstep.web.action.product.ProductDeleteAction;
import org.itstep.web.action.product.ProductEditAction;
import org.itstep.web.action.product.ProductListAction;
import org.itstep.web.action.product.ProductSaveAction;

public class Factory implements AutoCloseable {
	private Map<String, ActionFactory> actions = new HashMap<>();
	{
		ActionFactory mainActionFactory = () -> getMainAction();
		actions.put("/", mainActionFactory);
		actions.put("/index", mainActionFactory);
		actions.put("/login", () -> getLoginAction());
		actions.put("/logout", () -> getLogoutAction());
		actions.put("/product/list", () -> getProductListAction());
		actions.put("/product/edit", () -> getProductEditAction());
		actions.put("/product/save", () -> getProductSaveAction());
		actions.put("/product/delete", () -> getProductDeleteAction());
	}

	public Action getAction(String url) throws LogicException {
		ActionFactory factory = actions.get(url);
		if(factory != null) {
			return factory.getInstance();
		}
		return null;
	}

	private Action mainAction = null;
	public Action getMainAction() {
		if(mainAction == null) {
			mainAction = new MainAction();
		}
		return mainAction;
	}

	private Action loginAction = null;
	public Action getLoginAction() throws LogicException {
		if(loginAction == null) {
			LoginAction loginActionImpl = new LoginAction();
			loginAction = loginActionImpl;
			loginActionImpl.setUserService(getUserService());
		}
		return loginAction;
	}

	private Action logoutAction = null;
	public Action getLogoutAction() {
		if(logoutAction == null) {
			logoutAction = new LogoutAction();
		}
		return logoutAction;
	}

	private Action productListAction = null;
	public Action getProductListAction() throws LogicException {
		if(productListAction == null) {
			ProductListAction productListActionImpl = new ProductListAction();
			productListAction = productListActionImpl;
			productListActionImpl.setProductService(getProductService());
		}
		return productListAction;
	}

	private Action productEditAction = null;
	public Action getProductEditAction() throws LogicException {
		if(productEditAction == null) {
			ProductEditAction productEditActionImpl = new ProductEditAction();
			productEditAction = productEditActionImpl;
			productEditActionImpl.setProductService(getProductService());
			productEditActionImpl.setCategoryService(getCategoryService());
		}
		return productEditAction;
	}

	private Action productSaveAction = null;
	public Action getProductSaveAction() throws LogicException {
		if(productSaveAction == null) {
			ProductSaveAction productSaveActionImpl = new ProductSaveAction();
			productSaveAction = productSaveActionImpl;
			productSaveActionImpl.setProductService(getProductService());
		}
		return productSaveAction;
	}

	private Action productDeleteAction = null;
	public Action getProductDeleteAction() throws LogicException {
		if(productDeleteAction == null) {
			ProductDeleteAction productDeleteActionImpl = new ProductDeleteAction();
			productDeleteAction = productDeleteActionImpl;
			productDeleteActionImpl.setProductService(getProductService());
		}
		return productDeleteAction;
	}

	private CategoryService categoryService = null;
	public CategoryService getCategoryService() throws LogicException {
		if(categoryService == null) {
			CategoryServiceImpl service = new CategoryServiceImpl();
			categoryService = service;
			service.setCategoryDao(getCategoryDao());
		}
		return categoryService;
	}

	private ProductService productService = null;
	public ProductService getProductService() throws LogicException {
		if(productService == null) {
			ProductServiceImpl service = new ProductServiceImpl();
			productService = service;
			service.setProductDao(getProductDao());
			service.setCategoryDao(getCategoryDao());
		}
		return productService;
	}

	private UserService userService = null;
	public UserService getUserService() throws LogicException {
		if(userService == null) {
			UserServiceImpl service = new UserServiceImpl();
			userService = service;
			service.setUserDao(getUserDao());
		}
		return userService;
	}

	private ProductDao productDao = null;
	public ProductDao getProductDao() throws LogicException {
		if(productDao == null) {
			ProductDbDaoImpl productDaoImpl = new ProductDbDaoImpl();
			productDao = productDaoImpl;
			productDaoImpl.setConnection(getConnection());
		}
		return productDao;
	}

	private CategoryDao categoryDao = null;
	public CategoryDao getCategoryDao() throws LogicException {
		if(categoryDao == null) {
			CategoryDbDaoImpl categoryDaoImpl = new CategoryDbDaoImpl();
			categoryDao = categoryDaoImpl;
			categoryDaoImpl.setConnection(getConnection());
		}
		return categoryDao;
	}

	private UserDao userDao = null;
	public UserDao getUserDao() throws LogicException {
		if(userDao == null) {
			UserDbDaoImpl userDaoImpl = new UserDbDaoImpl();
			userDao = userDaoImpl;
			userDaoImpl.setConnection(getConnection());
		}
		return userDao;
	}

	private Connection connection = null;
	public Connection getConnection() throws LogicException {
		if(connection == null) {
			try {
				connection = ConnectionPool.getInstance().getConnection();
			} catch(ConnectionPoolException e) {
				throw new LogicException(e);
			}
		}
		return connection;
	}

	@Override
	public void close() {
		try { connection.close(); } catch(Exception e) {}
	}

	private static interface ActionFactory {
		Action getInstance() throws LogicException;
	}
}
