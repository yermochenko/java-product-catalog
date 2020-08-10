package org.itstep.logic;

import org.itstep.domain.User;

public interface UserService {
	User authenticate(String login, String password) throws LogicException;
}
