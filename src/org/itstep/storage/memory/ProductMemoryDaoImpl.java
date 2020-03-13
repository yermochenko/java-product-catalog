package org.itstep.storage.memory;

import org.itstep.domain.Product;
import org.itstep.storage.ProductDao;
import org.itstep.storage.DaoException;
import org.itstep.util.List;

public class ProductMemoryDaoImpl implements ProductDao {
	private List<Product> products = new List<>();
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
				products.del(i);
				break;
			}
		}
	}
}
