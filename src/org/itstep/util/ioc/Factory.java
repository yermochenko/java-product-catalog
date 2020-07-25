package org.itstep.util.ioc;

import java.lang.reflect.InvocationTargetException;
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
import org.itstep.storage.postgres.BaseDbDaoImpl;
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
	private static Map<Class<?>, Class<?>> iocContainer = new HashMap<>();
	static {
		// DAO
		iocContainer.put(UserDao.class, UserDbDaoImpl.class);
		iocContainer.put(CategoryDao.class, CategoryDbDaoImpl.class);
		iocContainer.put(ProductDao.class, ProductDbDaoImpl.class);
		// Services
		iocContainer.put(UserService.class, UserServiceImpl.class);
		iocContainer.put(CategoryService.class, CategoryServiceImpl.class);
		iocContainer.put(ProductService.class, ProductServiceImpl.class);
	}

	private Map<Class<?>, Object> cache = new HashMap<>();

	private static Map<Class<?>, DependencyInjector> injectors = new HashMap<>();
	static {
		// DAO
		DependencyInjector daoDI = (obj, factory) -> {
			BaseDbDaoImpl<?> userDbDaoImpl = (BaseDbDaoImpl<?>)obj;
			userDbDaoImpl.setConnection(factory.getConnection());
		};
		injectors.put(UserDbDaoImpl.class, daoDI);
		injectors.put(CategoryDbDaoImpl.class, daoDI);
		injectors.put(ProductDbDaoImpl.class, daoDI);
		// Services
		injectors.put(UserServiceImpl.class, (obj, factory) -> {
			UserServiceImpl service = (UserServiceImpl)obj;
			service.setUserDao(factory.get(UserDao.class));
		});
		injectors.put(CategoryServiceImpl.class, (obj, factory) -> {
			CategoryServiceImpl service = (CategoryServiceImpl)obj;
			service.setCategoryDao(factory.get(CategoryDao.class));
		});
		injectors.put(ProductServiceImpl.class, (obj, factory) -> {
			ProductServiceImpl service = (ProductServiceImpl)obj;
			service.setProductDao(factory.get(ProductDao.class));
			service.setCategoryDao(factory.get(CategoryDao.class));
		});
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> i) throws LogicException {
		Object o = cache.get(i);
		if(o == null) {
			Class<?> c = iocContainer.get(i);
			if(c != null) {
				try {
					o = c.getConstructor().newInstance();
					cache.put(i, o);
					injectors.get(c).inject(o, this);
				} catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					throw new ImplementationInstantiationIocContainerException(e);
				}
			} else {
				throw new InterfaceImplementationNotFoundIocContainerException();
			}
		}
		return (T)o;
	}

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
			loginActionImpl.setUserService(get(UserService.class));
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
			productListActionImpl.setProductService(get(ProductService.class));
		}
		return productListAction;
	}

	private Action productEditAction = null;
	public Action getProductEditAction() throws LogicException {
		if(productEditAction == null) {
			ProductEditAction productEditActionImpl = new ProductEditAction();
			productEditAction = productEditActionImpl;
			productEditActionImpl.setCategoryService(get(CategoryService.class));
			productEditActionImpl.setProductService(get(ProductService.class));
		}
		return productEditAction;
	}

	private Action productSaveAction = null;
	public Action getProductSaveAction() throws LogicException {
		if(productSaveAction == null) {
			ProductSaveAction productSaveActionImpl = new ProductSaveAction();
			productSaveAction = productSaveActionImpl;
			productSaveActionImpl.setProductService(get(ProductService.class));
		}
		return productSaveAction;
	}

	private Action productDeleteAction = null;
	public Action getProductDeleteAction() throws LogicException {
		if(productDeleteAction == null) {
			ProductDeleteAction productDeleteActionImpl = new ProductDeleteAction();
			productDeleteAction = productDeleteActionImpl;
			productDeleteActionImpl.setProductService(get(ProductService.class));
		}
		return productDeleteAction;
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

	private static interface DependencyInjector {
		void inject(Object obj, Factory factory) throws LogicException;
	}
}
