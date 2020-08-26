package org.itstep.storage.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.itstep.domain.Category;
import org.itstep.storage.CategoryDao;
import org.itstep.storage.DaoException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CategoryDbDaoImpl extends BaseDbDaoImpl<Category> implements CategoryDao {
	@Override
	protected Long createRaw(Category category) throws DaoException {
		String sql = "INSERT INTO \"category\"(\"name\") VALUES (?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // просим, чтобы statement МОГ получить ключи
			s.setString(1, category.getName());
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
	protected Category readRaw(Long id) throws DaoException {
		String sql = "SELECT \"name\" FROM \"category\" WHERE \"id\" = ?";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = getConnection().prepareStatement(sql);
			s.setLong(1, id);
			r = s.executeQuery();
			Category category = null;
			if(r.next()) {
				category = new Category();
				category.setId(id);
				category.setName(r.getString("name"));
			}
			return category;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected void updateRaw(Category category) throws DaoException {
		String sql = "UPDATE \"category\" SET \"name\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = getConnection().prepareStatement(sql);
			s.setString(1, category.getName());
			s.setLong(2, category.getId());
			s.executeUpdate();
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

	@Override
	protected String getTableName() {
		return "category";
	}
}
