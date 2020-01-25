package org.itstep.logic;

import org.itstep.domain.Product;
import org.itstep.storage.ProductStorage;
import org.itstep.util.List;

public class ProductServiceImpl implements ProductService {
	private ProductStorage productStorage;

	public void setProductStorage(ProductStorage productStorage) {
		this.productStorage = productStorage;
	}

	@Override
	public List<Product> findAll() {
		return productStorage.read();
	}

	@Override
	public void save(Product product) {
		if(product.getId() == null) {
			productStorage.create(product);
		} else {
			productStorage.update(product);
		}
	}

	@Override
	public void delete(Long id) {
		productStorage.delete(id);
	}
}
