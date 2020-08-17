package com.datos.mern.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.datos.mern.model.User;
import com.datos.mern.exception.EntityPersistenceException;



@Component

public class UserDAO {
	private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
	
	@Autowired
	private DataSource dataSource;
	
	private DataSource getDataSource() {
		if (dataSource == null) {
			throw new RuntimeException("DataSource is null (configuration error, perhaps?)");
		}
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<User> findAll() {
	    List<User> ret;
	    String sql = "SELECT * FROM " + User.TABLE_NAME;
	    JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
	    ret = jdbc.query(sql, new ItemRowMapper());
	    if (ret.size() > 1) {
	      log.info("Found " + ret.size() + " rows from query");
	    }
	    return ret;
	}
	
	public User findById(Long id) {
	    User ret = null;
	    String sql = "SELECT * FROM " + User.TABLE_NAME + " WHERE id = ?";
	    Object[] paramValues = { id };
	    JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
	    List<User> items = jdbc.query(sql, paramValues, new ItemRowMapper());
	    if (items.size() > 1) {
	      throw new RuntimeException("Expected 1 result from findById(), instead found " + items.size()
	          + " (DB configuration error, maybe?)");
	    }
	    if (!items.isEmpty()) {
	      ret = items.get(0);
	    }
	    return ret;
	}
	
	public User findByName(String name) {
	    User ret = null;
	    String sql = "SELECT * FROM " + User.TABLE_NAME + " WHERE nombre = ?";
	    Object[] paramValues = { name };
	    JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
	    List<User> items = jdbc.query(sql, paramValues, new ItemRowMapper());
	    if (items.size() > 1) {
	      throw new RuntimeException("Expected 1 result from findByName(), instead found " + items.size()
	          + " (DB configuration error, maybe?)");
	    }
	    if (!items.isEmpty()) {
	      ret = items.get(0);
	    }
	    return ret;
	}
	
	public User findByDescription(String description) {
		User ret = null;
		String sql = "SELECT * FROM " + User.TABLE_NAME + " WHERE description = ?";
		Object[] paramValues = { description };
		JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
		List<User> items = jdbc.query(sql, paramValues, new ItemRowMapper());
	    if (items.size() > 1) {
	      throw new RuntimeException("Expected 1 result from findById(), instead found " + items.size()
	          + " (DB configuration error, maybe?)");
	    }
	    if (!items.isEmpty()) {
	      ret = items.get(0);
	    }
	    return ret;
	}
	
	public User add(User item) throws EntityPersistenceException {
	    User ret = null;
	    //
	    String sql = "INSERT INTO " + User.TABLE_NAME + "(NOMBRE, DESCRIPTION) VALUES(?, ?)";
	    Object[] paramValues = {
	        item.get_Nombre(),
	        item.get_Description()
	    };
	    JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
	    try {
	      int numRowsAffected = jdbc.update(sql, paramValues);
	      if (numRowsAffected == 1) {
	        ret = findByDescription(item.get_Description());
	      } else {
	        String message = "Expected 1 row to be affected by INSERT, instead " + numRowsAffected
	            + " were affected (DB configuration error, maybe?)";
	        log.error(message);
	        throw new EntityPersistenceException(message);
	      }
	    } catch (DataAccessException e) {
	      String message = "Exception occurred while inserting record";
	      log.error(message, e);
	      throw new EntityPersistenceException(e);
	    }
	    return ret;
	}
	
	public boolean update(User item) {
	    boolean ret = false;
	    //
	    String sql = "UPDATE " + User.TABLE_NAME + " SET nombre=?, description=? WHERE id=?";
	    Object[] paramValues = {
	        item.get_Nombre(),
	        item.get_Description(),
	        item.get_Id()
	    };
	    JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
	    try {
	      log.info("Attempting to update " + User.TABLE_NAME + " table with object "
	          + ReflectionToStringBuilder.toString(item));
	      int numRowsAffected = jdbc.update(sql, paramValues);
	      if (numRowsAffected == 1) {
	        ret = true;
	      } else {
	        String message = "Expected 1 row to be affected by UPDATE, instead " + numRowsAffected
	            + " were affected (bad ID, maybe?)";
	        log.error(message);
	      }
	    } catch (DataAccessException e) {
	      String message = "Exception occurred while inserting record";
	      log.error(message, e);
	    }
	    return ret;
	}
	
	public User delete(User item) throws EntityPersistenceException {
	    User ret = null;
	    //
	    String sql = "DELETE FROM " + User.TABLE_NAME + " WHERE id = ?";
	    Object[] paramValues = { item.get_Id() };
	    JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
	    try {
	      int numRowsAffected = jdbc.update(sql, paramValues);
	      if (numRowsAffected != 1) {
	        String message = "Expected 1 row to be affected by DELETE, instead " + numRowsAffected
	            + " were affected (bad ID, maybe?)";
	        log.error(message);
	        throw new EntityPersistenceException(message);
	      }
	      ret = item;
	    } catch (DataAccessException e) {
	      String message = "Exception occurred while inserting record";
	      log.error(message, e);
	      throw new EntityPersistenceException(e);
	    }
	    return ret;
	}
	
	public class ItemRowMapper implements RowMapper<User> {
	    @Override
	    public User mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
	      return new User()
	    	  .set_Id(resultSet.getLong("id"))  
	          .set_Nombre(resultSet.getString("nombre"))
	          .set_Descripcion(resultSet.getString("description"));
	    }
	}	
}
