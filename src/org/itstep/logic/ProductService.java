package org.itstep.logic;

import java.util.List;

import org.itstep.domain.Product;

public interface ProductService {
	List<Product> findAll() throws LogicException;

	List<Product> findNamesBySearchString(String search) throws LogicException;

	Product findById(Long id) throws LogicException;

	void save(Product product) throws LogicException;

	void delete(List<Long> ids) throws LogicException;
}
