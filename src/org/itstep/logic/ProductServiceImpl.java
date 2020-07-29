package org.itstep.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itstep.domain.Category;
import org.itstep.domain.Product;
import org.itstep.storage.ProductDao;
import org.itstep.storage.CategoryDao;
import org.itstep.storage.DaoException;

public class ProductServiceImpl implements ProductService {
	private ProductDao productDao;
	private CategoryDao categoryDao;

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	public List<Product> findAll() throws LogicException {
		try {
			List<Product> products = productDao.read();
			for(Product product : products) {
				Category category = product.getCategory();
				category = categoryDao.read(category.getId());
				product.setCategory(category);
			}
			return products;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public List<Product> findNamesBySearchString(String search) throws LogicException {
		try {
			return productDao.readBySearchString(search);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Map<Category, List<Product>> findLatest() throws LogicException {
		try {
			Map<Category, List<Product>> result = new HashMap<>();
			List<Product> products = productDao.readLatest();
			for(Product product : products) {
				Category category = product.getCategory();
				category = categoryDao.read(category.getId());
				product.setCategory(category);
				List<Product> categorysProducts = result.get(category);
				if(categorysProducts == null) {
					categorysProducts = new ArrayList<>();
					result.put(category, categorysProducts);
				}
				categorysProducts.add(product);
			}
			return result;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Product findById(Long id) throws LogicException {
		try {
			Product product = productDao.read(id);
			if(product != null) {
				Category category = product.getCategory();
				category = categoryDao.read(category.getId());
				product.setCategory(category);
			}
			return product;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void save(Product product) throws LogicException {
		try {
			product.setDate(new Date());
			if(product.getId() == null) {
				Long id = productDao.create(product);
				product.setId(id);
			} else {
				productDao.update(product);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				productDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
}
