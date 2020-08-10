package org.itstep.logic;

import org.itstep.domain.User;
import org.itstep.storage.DaoException;
import org.itstep.storage.UserDao;

public class UserServiceImpl implements UserService {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User authenticate(String login, String password) throws LogicException {
		try {
			return userDao.read(login, password);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
}
