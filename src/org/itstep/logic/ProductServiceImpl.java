package org.itstep.logic;

import org.itstep.domain.Product;
import org.itstep.storage.ProductStorage;
import org.itstep.storage.StorageException;
import org.itstep.util.List;

public class ProductServiceImpl implements ProductService {
	private ProductStorage productStorage;

	public void setProductStorage(ProductStorage productStorage) {
		this.productStorage = productStorage;
	}

	@Override
	public List<Product> findAll() throws LogicException {
		try {
			return productStorage.read();
		} catch(StorageException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void save(Product product) throws LogicException {
		try {
			if(product.getId() == null) {
				productStorage.create(product);
			} else {
				productStorage.update(product);
			}
		} catch(StorageException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			productStorage.delete(id);
		} catch(StorageException e) {
			throw new LogicException(e);
		}
	}
}
