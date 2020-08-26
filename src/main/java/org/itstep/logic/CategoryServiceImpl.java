package org.itstep.logic;

import java.util.List;

import org.itstep.domain.Category;
import org.itstep.storage.CategoryDao;
import org.itstep.storage.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao categoryDao;

	@Override
	public List<Category> findAll() throws LogicException {
		try {
			return categoryDao.read();
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Category findById(Long id) throws LogicException {
		try {
			return categoryDao.read(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void save(Category category) throws LogicException {
		try {
			if(category.getId() == null) {
				Long id = categoryDao.create(category);
				category.setId(id);
			} else {
				categoryDao.update(category);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			categoryDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
}
