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

	private static Map<String, Class<?>> actions = new HashMap<>();
	static {
		actions.put("/", MainAction.class);
		actions.put("/index", MainAction.class);
		actions.put("/login", LoginAction.class);
		actions.put("/logout", LogoutAction.class);
		actions.put("/product/list", ProductListAction.class);
		actions.put("/product/edit", ProductEditAction.class);
		actions.put("/product/save", ProductSaveAction.class);
		actions.put("/product/delete", ProductDeleteAction.class);
	}

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
		// Actions
		injectors.put(LoginAction.class, (obj, factory) -> {
			LoginAction loginAction = (LoginAction)obj;
			loginAction.setUserService(factory.get(UserService.class));
		});
		injectors.put(ProductListAction.class, (obj, factory) -> {
			ProductListAction productListAction = (ProductListAction)obj;
			productListAction.setCategoryService(factory.get(CategoryService.class));
			productListAction.setProductService(factory.get(ProductService.class));
		});
		injectors.put(ProductEditAction.class, (obj, factory) -> {
			ProductEditAction productEditAction = (ProductEditAction)obj;
			productEditAction.setCategoryService(factory.get(CategoryService.class));
			productEditAction.setProductService(factory.get(ProductService.class));
		});
		injectors.put(ProductSaveAction.class,	(obj, factory) -> {
			ProductSaveAction productSaveAction = (ProductSaveAction)obj;
			productSaveAction.setProductService(factory.get(ProductService.class));
		});
		injectors.put(ProductDeleteAction.class, (obj, factory) -> {
			ProductDeleteAction productDeleteAction = (ProductDeleteAction)obj;
			productDeleteAction.setProductService(factory.get(ProductService.class));
		});
	}

	private Map<Class<?>, Object> cache = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> i) throws LogicException {
		Object o = cache.get(i);
		if(o == null) {
			Class<?> c;
			if(i.isInterface()) {
				c = iocContainer.get(i);
			} else {
				c = i;
			}
			if(c != null) {
				try {
					o = c.getConstructor().newInstance();
					cache.put(i, o);
					DependencyInjector injector = injectors.get(c);
					if(injector != null) {
						injector.inject(o, this);
					}
				} catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					throw new ImplementationInstantiationIocContainerException(e);
				}
			} else {
				throw new InterfaceImplementationNotFoundIocContainerException();
			}
		}
		return (T)o;
	}

	public Action getAction(String url) throws LogicException {
		return (Action)get(actions.get(url));
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

	private static interface DependencyInjector {
		void inject(Object obj, Factory factory) throws LogicException;
	}
}
