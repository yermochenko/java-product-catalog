package org.itstep.storage.memory;

import java.util.ArrayList;
import java.util.List;

import org.itstep.domain.Product;
import org.itstep.storage.ProductDao;
import org.itstep.storage.DaoException;

public class ProductMemoryDaoImpl implements ProductDao {
	private List<Product> products = new ArrayList<>();
	private Long lastCreatedId = 0L;

	@Override
	public Long create(Product product) {
		lastCreatedId++;
		product.setId(lastCreatedId);
		products.add(product);
		return lastCreatedId;
	}

	@Override
	public Product read(Long id) {
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).getId().equals(id)) {
				return products.get(i);
			}
		}
		return null;
	}

	@Override
	public List<Product> read() {
		return products;
	}

	@Override
	public void update(Product product) throws DaoException {
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).getId().equals(product.getId())) {
				products.set(i, product);
				return;
			}
		}
		throw new DaoException();
	}

	@Override
	public void delete(Long id) {
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).getId().equals(id)) {
				products.remove(i);
				break;
			}
		}
	}

	@Override
	public List<Product> readBySearchString(String search) throws DaoException {
		return products;
	}
}
