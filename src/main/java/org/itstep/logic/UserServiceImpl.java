package org.itstep.logic;

import org.itstep.domain.User;
import org.itstep.storage.DaoException;
import org.itstep.storage.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public User authenticate(String login, String password) throws LogicException {
		try {
			return userDao.read(login, password);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
}
