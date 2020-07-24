package org.itstep.storage.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.itstep.domain.Role;
import org.itstep.domain.User;
import org.itstep.storage.DaoException;
import org.itstep.storage.UserDao;

public class UserDbDaoImpl extends BaseDbDaoImpl implements UserDao {
	private Map<Long, User> cache = new HashMap<>();

	@Override
	public Long create(User user) throws DaoException {
		String sql = "INSERT INTO \"user\"(\"login\", \"password\", \"role\") VALUES (?, ?, ?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, user.getLogin());
			s.setString(2, user.getPassword());
			s.setInt(3, user.getRole().ordinal());
			s.executeUpdate();
			r = s.getGeneratedKeys();
			r.next();
			cache.clear();
			return r.getLong(1);
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
			try { r.close(); } catch(Exception e) {}
		}
	}

	@Override
	public User read(Long id) throws DaoException {
		String sql = "SELECT \"login\", \"password\", \"role\" FROM \"user\" WHERE \"id\" = ?";
		User user = cache.get(id);
		if(user == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = getConnection().prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					user = new User();
					user.setId(id);
					user.setLogin(r.getString("login"));
					user.setPassword(r.getString("password"));
					user.setRole(Role.values()[r.getInt("role")]);
					cache.put(id, user);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return user;
	}

	@Override
	public void update(User user) throws DaoException {
		String sql = "UPDATE \"user\" SET \"login\" = ?, \"password\" = ?, \"role\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = getConnection().prepareStatement(sql);
			s.setString(1, user.getLogin());
			s.setString(2, user.getPassword());
			s.setInt(3, user.getRole().ordinal());
			s.setLong(4, user.getId());
			s.executeUpdate();
			cache.clear();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
		}
	}

	@Override
	public void delete(Long id) throws DaoException {
		String sql = "DELETE FROM \"user\" WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = getConnection().prepareStatement(sql);
			s.setLong(1, id);
			s.executeUpdate();
			cache.clear();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
		}
	}

	@Override
	public User read(String login, String password) throws DaoException {
		String sql = "SELECT \"id\", \"role\" FROM \"user\" WHERE \"login\" = ? AND \"password\" = ?";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = getConnection().prepareStatement(sql);
			s.setString(1, login);
			s.setString(2, password);
			r = s.executeQuery();
			User user = null;
			if(r.next()) {
				user = new User();
				user.setId(r.getLong("id"));
				user.setLogin(login);
				user.setPassword(password);
				user.setRole(Role.values()[r.getInt("role")]);
			}
			return user;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
}
