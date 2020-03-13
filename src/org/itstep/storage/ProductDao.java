package org.itstep.storage;

import org.itstep.domain.Product;
import org.itstep.util.List;

public interface ProductDao extends Dao<Product> {
	List<Product> read() throws DaoException;
}
