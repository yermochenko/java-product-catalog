package org.itstep.storage;

import org.itstep.domain.Product;
import org.itstep.util.List;

public interface ProductStorage {
	Long create(Product product);

	Product read(Long id);

	List<Product> read();

	void update(Product product);

	void delete(Long id);
}
