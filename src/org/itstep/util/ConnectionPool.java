package org.itstep.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
	private int max = 30;
	private Queue<Connection> freeConnections = new LinkedBlockingQueue<>();
	private Set<Connection> usedConnections = new ConcurrentSkipListSet<>();

	public Connection getConnection() {
		Connection connection = null;
		while(connection == null) {
			if(!freeConnections.isEmpty()) {
				connection = freeConnections.poll();
				try {
					if(!connection.isValid(100)) {
						try { connection.close(); } catch(SQLException e) {} // TODO: add thread-closer
						connection = null;
					}
				} catch(SQLException e) {
					connection = null;
				}
			} else if(usedConnections.size() < max) {
				// TODO: DriverManager.getConnection()
			} else {
				// TODO: throw exception
			}
		}
		usedConnections.add(connection);
		return connection;
	}

	public void freeConnection(Connection connection) {
		usedConnections.remove(connection);
		freeConnections.add(connection);
	}
}
