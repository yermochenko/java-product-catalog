package org.itstep.logic;

import java.util.List;
import java.util.Map;

import org.itstep.domain.Category;
import org.itstep.domain.Product;

public interface ProductService {
	List<Product> findByCategory(Long categoryId) throws LogicException;

	List<Product> findNamesBySearchString(String search) throws LogicException;

	Map<Category, List<Product>> findLatest() throws LogicException;

	Product findById(Long id) throws LogicException;

	void save(Product product) throws LogicException;

	void delete(List<Long> ids) throws LogicException;
}
