package org.itstep.logic;

import org.itstep.domain.Product;
import org.itstep.util.List;

public interface ProductService {
	List<Product> findAll() throws LogicException;

	void save(Product product) throws LogicException;

	void delete(Long id) throws LogicException;
}
