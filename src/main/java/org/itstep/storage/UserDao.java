package org.itstep.storage;

import org.itstep.domain.User;

public interface UserDao extends Dao<User> {
	User read(String login, String password) throws DaoException;
}
