package org.itstep.logic;

import java.util.List;

import org.itstep.domain.Category;

public interface CategoryService {
	List<Category> findAll() throws LogicException;

	Category findById(Long id) throws LogicException;

	void save(Category category) throws LogicException;

	void delete(Long id) throws LogicException;
}
