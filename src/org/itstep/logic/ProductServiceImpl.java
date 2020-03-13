package org.itstep.logic;

import org.itstep.domain.Product;
import org.itstep.storage.ProductDao;
import org.itstep.storage.DaoException;
import org.itstep.util.List;

public class ProductServiceImpl implements ProductService {
	private ProductDao productStorage;

	public void setProductStorage(ProductDao productStorage) {
		this.productStorage = productStorage;
	}

	@Override
	public List<Product> findAll() throws LogicException {
		try {
			return productStorage.read();
		} catch(DaoException e) {
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
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			productStorage.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
}
