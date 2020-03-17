package org.itstep.storage.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.itstep.domain.Product;
import org.itstep.storage.DaoException;
import org.itstep.storage.ProductDao;

public class ProductDbDaoImpl implements ProductDao {
	private Connection c;

	public void setConnection(Connection c) {
		this.c = c;
	}

	@Override
	public Long create(Product entity) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product read(Long id) throws DaoException {
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement("SELECT \"category\", \"name\", \"price\", \"amount\", \"date\" FROM \"product\" WHERE \"id\" = ?");
			s.setLong(1, id);
			r = s.executeQuery();
			Product product = null;
			if(r.next()) {
				product = new Product();
				product.setId(id);
				product.setCategory(r.getString("category"));
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
	public void update(Product product) throws DaoException {
		PreparedStatement s = null;
		try {
			s = c.prepareStatement("UPDATE \"product\" SET \"category\" = ?, \"name\" = ?, \"price\" = ?, \"amount\" = ?, \"date\" = ? WHERE \"id\" = ?");
			s.setString(1, product.getCategory());
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
	public void delete(Long id) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> read() throws DaoException {
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery("SELECT \"id\", \"category\", \"name\", \"price\", \"amount\", \"date\" FROM \"product\"");
			List<Product> products = new ArrayList<>();
			while(r.next()) {
				Product product = new Product();
				product.setId(r.getLong("id"));
				product.setCategory(r.getString("category"));
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
}
