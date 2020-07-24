package org.itstep.storage.postgres;

import java.sql.Connection;

abstract public class BaseDbDaoImpl {
	private Connection c;

	protected final Connection getConnection() {
		return c;
	}

	public final void setConnection(Connection c) {
		this.c = c;
	}
}
