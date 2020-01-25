package org.itstep.logic;

import org.itstep.domain.Product;
import org.itstep.util.List;

public interface ProductService {
	List<Product> findAll();

	void save(Product product);

	void delete(Long id);
}
