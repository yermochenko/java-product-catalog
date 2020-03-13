package org.itstep.logic;

import java.util.List;

import org.itstep.domain.Product;

public interface ProductService {
	List<Product> findAll() throws LogicException;

	void save(Product product) throws LogicException;

	void delete(Long id) throws LogicException;
}
