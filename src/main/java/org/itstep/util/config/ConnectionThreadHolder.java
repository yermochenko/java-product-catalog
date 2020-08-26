package org.itstep.util.config;

import java.sql.Connection;

public class ConnectionThreadHolder {
	private static final ThreadLocal<Connection> connectionThreadScope = new ThreadLocal<>();

	public static Connection getConnection() {
		return connectionThreadScope.get();
	}

	public static void setConnection(Connection connection) {
		connectionThreadScope.set(connection);
	}

	public static void removeConnection() {
		connectionThreadScope.remove();
	}
}
