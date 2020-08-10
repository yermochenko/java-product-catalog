package org.itstep.web;

import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itstep.logic.LogicException;
import org.itstep.util.ioc.Factory;
import org.itstep.util.pool.ConnectionPool;
import org.itstep.util.pool.ConnectionPoolException;

public class ApplicationInitializer implements ServletContextListener {
	private static final Logger logger = LogManager.getLogger();
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			ServletContext context = sce.getServletContext();
			String jdbcDriver =   context.getInitParameter("jdbc-driver");
			String jdbcUrl =      context.getInitParameter("jdbc-url");
			String jdbcUser =     context.getInitParameter("jdbc-user");
			String jdbcPassword = context.getInitParameter("jdbc-password");
			int connectionPoolMinSize =           Integer.parseInt(context.getInitParameter("connection-pool-min-size"));
			int connectionPoolMaxSize =           Integer.parseInt(context.getInitParameter("connection-pool-max-size"));
			int connectionPoolValidationTimeout = Integer.parseInt(context.getInitParameter("connection-pool-validation-timeout"));
			ConnectionPool.getInstance().init(jdbcDriver, jdbcUrl, jdbcUser, jdbcPassword, connectionPoolMinSize, connectionPoolMaxSize, connectionPoolValidationTimeout);
			Factory.addCustomFactory(Connection.class, () -> {
				try {
					return ConnectionPool.getInstance().getConnection();
				} catch (ConnectionPoolException e) {
					LogManager.getLogger(ConnectionPool.class).error("Can't get connection from pool", e);
					throw new LogicException(e);
				}
			});
			logger.info("Application was successfull initialized");
		} catch(ConnectionPoolException | NumberFormatException e) {
			logger.fatal("Can't initialize application", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ConnectionPool.getInstance().destroy();
		logger.info("Application was successfull destroyed");
	}
}
