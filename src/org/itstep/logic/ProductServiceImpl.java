package org.itstep.logic;

import java.util.List;

import org.itstep.domain.Product;
import org.itstep.storage.ProductDao;
import org.itstep.storage.DaoException;

public class ProductServiceImpl implements ProductService {
	private ProductDao productDao;

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public List<Product> findAll() throws LogicException {
		try {
			return productDao.read();
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void save(Product product) throws LogicException {
		try {
			if(product.getId() == null) {
				productDao.create(product);
			} else {
				productDao.update(product);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			productDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
}
