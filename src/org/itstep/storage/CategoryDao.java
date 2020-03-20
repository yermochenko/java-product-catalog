package org.itstep.storage;

import java.util.List;

import org.itstep.domain.Category;

public interface CategoryDao extends Dao<Category> {
	List<Category> read() throws DaoException;
}
