package org.itstep.util.config;

import java.sql.Connection;

import org.springframework.beans.factory.FactoryBean;

public class ConnectionFactory implements FactoryBean<Connection> {
	@Override
	public Connection getObject() throws Exception {
		Connection connection = ConnectionThreadHolder.getConnection();
		if(connection != null) {
			return connection;
		} else {
			throw new OpenConnectionException();
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Connection.class;
	}
}
