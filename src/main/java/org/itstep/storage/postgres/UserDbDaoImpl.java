package org.itstep.storage.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.itstep.domain.Role;
import org.itstep.domain.User;
import org.itstep.storage.DaoException;
import org.itstep.storage.UserDao;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UserDbDaoImpl extends BaseDbDaoImpl<User> implements UserDao {
	@Override
	protected Long createRaw(User user) throws DaoException {
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
			return r.getLong(1);
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
			try { r.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected User readRaw(Long id) throws DaoException {
		String sql = "SELECT \"login\", \"password\", \"role\" FROM \"user\" WHERE \"id\" = ?";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = getConnection().prepareStatement(sql);
			s.setLong(1, id);
			r = s.executeQuery();
			User user = null;
			if(r.next()) {
				user = new User();
				user.setId(id);
				user.setLogin(r.getString("login"));
				user.setPassword(r.getString("password"));
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

	@Override
	protected void updateRaw(User user) throws DaoException {
		String sql = "UPDATE \"user\" SET \"login\" = ?, \"password\" = ?, \"role\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = getConnection().prepareStatement(sql);
			s.setString(1, user.getLogin());
			s.setString(2, user.getPassword());
			s.setInt(3, user.getRole().ordinal());
			s.setLong(4, user.getId());
			s.executeUpdate();
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

	@Override
	protected String getTableName() {
		return "user";
	}
}
