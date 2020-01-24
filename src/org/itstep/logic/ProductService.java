package org.itstep.logic;

import org.itstep.domain.Product;
import org.itstep.storage.ProductStorage;
import org.itstep.util.List;

public class ProductService {
	private ProductStorage productStorage;

	public void setProductStorage(ProductStorage productStorage) {
		this.productStorage = productStorage;
	}

	public List<Product> findAll() {
		return productStorage.read();
	}

	public void save(Product product) {
		if(product.getId() == null) {
			productStorage.create(product);
		} else {
			productStorage.update(product);
		}
	}

	public void delete(Long id) {
		productStorage.delete(id);
	}
}
