package org.itstep.storage.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.itstep.domain.Entity;
import org.itstep.storage.Dao;
import org.itstep.storage.DaoException;
import org.springframework.beans.factory.annotation.Autowired;

abstract public class BaseDbDaoImpl<T extends Entity> implements Dao<T> {
	@Autowired
	private Connection c;

	private Map<Long, T> cache = new HashMap<>();

	protected final Connection getConnection() {
		return c;
	}

	@Override
	public final Long create(T entity) throws DaoException {
		cache.clear();
		Long id = createRaw(entity);
		return id;
	}

	@Override
	public final T read(Long id) throws DaoException {
		T entity = cache.get(id);
		if(entity == null) {
			entity = readRaw(id);
			cache.put(id, entity);
		}
		return entity;
	}

	@Override
	public final void update(T entity) throws DaoException {
		cache.clear();
		updateRaw(entity);
	}

	@Override
	public final void delete(Long id) throws DaoException {
		cache.clear();
		String sql = String.format("DELETE FROM \"%s\" WHERE \"id\" = ?", getTableName());
		PreparedStatement s = null;
		try {
			s = getConnection().prepareStatement(sql);
			s.setLong(1, id);
			s.executeUpdate();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
		}
	}

	abstract protected Long createRaw(T entity) throws DaoException;

	abstract protected T readRaw(Long id) throws DaoException;

	abstract protected void updateRaw(T entity) throws DaoException;

	abstract protected String getTableName();
}
