package org.itstep.web;

import java.sql.Connection;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itstep.util.config.ConnectionThreadHolder;
import org.itstep.util.pool.ConnectionPool;
import org.itstep.util.pool.ConnectionPoolException;

public class RequestConnectionBinding implements ServletRequestListener {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionThreadHolder.setConnection(connection);
			logger.debug("Connection bind to request successfull");
		} catch(ConnectionPoolException e) {
			logger.error("Can't connect to database", e);
		}
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		Connection connection = ConnectionThreadHolder.getConnection();
		try {
			ConnectionThreadHolder.removeConnection();
			connection.close();
			logger.debug("Connection unbind successfull");
		} catch(Exception e) {
			logger.warn("Can't close connection");
		}
	}
}
