package org.itstep.storage.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itstep.domain.Category;
import org.itstep.storage.CategoryDao;
import org.itstep.storage.DaoException;

public class CategoryDbDaoImpl extends BaseDbDaoImpl implements CategoryDao {
	private Map<Long, Category> cache = new HashMap<>();

	@Override
	public Long create(Category category) throws DaoException {
		String sql = "INSERT INTO \"category\"(\"name\") VALUES (?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // просим, чтобы statement МОГ получить ключи
			s.setString(1, category.getName());
			s.executeUpdate();
			r = s.getGeneratedKeys(); // ПОЛУЧАЕМ сгенерированные ключи (не работает без Statement.RETURN_GENERATED_KEYS)
			r.next();
			cache.clear();
			return r.getLong(1);
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
			try { r.close(); } catch(Exception e) {}
		}
	}

	@Override
	public Category read(Long id) throws DaoException {
		String sql = "SELECT \"name\" FROM \"category\" WHERE \"id\" = ?";
		Category category = cache.get(id);
		if(category == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = getConnection().prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					category = new Category();
					category.setId(id);
					category.setName(r.getString("name"));
					cache.put(id, category);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return category;
	}

	@Override
	public void update(Category category) throws DaoException {
		String sql = "UPDATE \"category\" SET \"name\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = getConnection().prepareStatement(sql);
			s.setString(1, category.getName());
			s.setLong(2, category.getId());
			s.executeUpdate();
			cache.clear();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
		}
	}

	@Override
	public void delete(Long id) throws DaoException {
		String sql = "DELETE FROM \"category\" WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = getConnection().prepareStatement(sql);
			s.setLong(1, id);
			s.executeUpdate();
			cache.clear();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
		}
	}

	@Override
	public List<Category> read() throws DaoException {
		String sql = "SELECT \"id\", \"name\" FROM \"category\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = getConnection().createStatement();
			r = s.executeQuery(sql);
			List<Category> categories = new ArrayList<>();
			while(r.next()) {
				Category category = new Category();
				category.setId(r.getLong("id"));
				category.setName(r.getString("name"));
				categories.add(category);
			}
			return categories;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
}
