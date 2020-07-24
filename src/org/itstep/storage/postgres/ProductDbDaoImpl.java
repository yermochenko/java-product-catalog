package org.itstep.storage.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.itstep.domain.Category;
import org.itstep.domain.Product;
import org.itstep.storage.DaoException;
import org.itstep.storage.ProductDao;

public class ProductDbDaoImpl extends BaseDbDaoImpl<Product> implements ProductDao {
	@Override
	protected Long createRaw(Product product) throws DaoException {
		String sql = "INSERT INTO \"product\"(\"category_id\", \"name\", \"price\", \"amount\", \"date\") VALUES (?, ?, ?, ?, ?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // просим, чтобы statement МОГ получить ключи
			s.setLong(1, product.getCategory().getId());
			s.setString(2, product.getName());
			s.setLong(3, product.getPrice());
			s.setInt(4, product.getAmount());
			s.setDate(5, new java.sql.Date(product.getDate().getTime()));
			s.executeUpdate();
			r = s.getGeneratedKeys(); // ПОЛУЧАЕМ сгенерированные ключи (не работает без Statement.RETURN_GENERATED_KEYS)
			r.next();
			return r.getLong(1);
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
			try { r.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected Product readRaw(Long id) throws DaoException {
		String sql = "SELECT \"category_id\", \"name\", \"price\", \"amount\", \"date\" FROM \"product\" WHERE \"id\" = ?";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = getConnection().prepareStatement(sql);
			s.setLong(1, id);
			r = s.executeQuery();
			Product product = null;
			if(r.next()) {
				product = new Product();
				product.setId(id);
				product.setCategory(new Category());
				product.getCategory().setId(r.getLong("category_id"));
				product.setName(r.getString("name"));
				product.setPrice(r.getLong("price"));
				product.setAmount(r.getInt("amount"));
				product.setDate(new java.util.Date(r.getDate("date").getTime()));
			}
			return product;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected void updateRaw(Product product) throws DaoException {
		String sql = "UPDATE \"product\" SET \"category_id\" = ?, \"name\" = ?, \"price\" = ?, \"amount\" = ?, \"date\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = getConnection().prepareStatement(sql);
			s.setLong(1, product.getCategory().getId());
			s.setString(2, product.getName());
			s.setLong(3, product.getPrice());
			s.setInt(4, product.getAmount());
			s.setDate(5, new java.sql.Date(product.getDate().getTime()));
			s.setLong(6, product.getId());
			s.executeUpdate();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
		}
	}

	@Override
	public List<Product> read() throws DaoException {
		String sql = "SELECT \"id\", \"category_id\", \"name\", \"price\", \"amount\", \"date\" FROM \"product\" ORDER BY \"name\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = getConnection().createStatement();
			r = s.executeQuery(sql);
			List<Product> products = new ArrayList<>();
			while(r.next()) {
				Product product = new Product();
				product.setId(r.getLong("id"));
				product.setCategory(new Category());
				product.getCategory().setId(r.getLong("category_id"));
				product.setName(r.getString("name"));
				product.setPrice(r.getLong("price"));
				product.setAmount(r.getInt("amount"));
				product.setDate(new java.util.Date(r.getDate("date").getTime()));
				products.add(product);
			}
			return products;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}

	@Override
	public List<Product> readBySearchString(String search) throws DaoException {
		String sql = String.format("SELECT \"id\", \"category_id\", \"name\", \"price\", \"amount\", \"date\" FROM \"product\" WHERE \"name\" LIKE '%%%s%%' ORDER BY \"name\"", search);
		Statement s = null;
		ResultSet r = null;
		try {
			s = getConnection().createStatement();
			r = s.executeQuery(sql);
			List<Product> products = new ArrayList<>();
			while(r.next()) {
				Product product = new Product();
				product.setId(r.getLong("id"));
				product.setCategory(new Category());
				product.getCategory().setId(r.getLong("category_id"));
				product.setName(r.getString("name"));
				product.setPrice(r.getLong("price"));
				product.setAmount(r.getInt("amount"));
				product.setDate(new java.util.Date(r.getDate("date").getTime()));
				products.add(product);
			}
			return products;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected String getTableName() {
		return "product";
	}
}
