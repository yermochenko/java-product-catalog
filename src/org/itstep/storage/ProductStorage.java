package org.itstep.storage;

import org.itstep.domain.Product;
import org.itstep.util.List;

public interface ProductStorage {
	Long create(Product product) throws StorageException;

	Product read(Long id) throws StorageException;

	List<Product> read() throws StorageException;

	void update(Product product) throws StorageException;

	void delete(Long id) throws StorageException;
}
