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

import com.datos.mern.model.Notes;
import com.datos.mern.exception.EntityPersistenceException;

@Component

public class NotesDAO {
	private static final Logger log = LoggerFactory.getLogger(NotesDAO.class);
	
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
	
	public List<Notes> findAll() {
	    List<Notes> ret;
	    String sql = "SELECT * FROM " + Notes.TABLE_NAME;
	    JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
	    ret = jdbc.query(sql, new ItemRowMapper());
	    if (ret.size() > 1) {
	      log.info("Found " + ret.size() + " rows from query");
	    }
	    return ret;
	}
	
	public Notes findById(Long id) {
	    Notes ret = null;
	    String sql = "SELECT * FROM " + Notes.TABLE_NAME + " WHERE id = ?";
	    Object[] paramValues = { id };
	    JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
	    List<Notes> items = jdbc.query(sql, paramValues, new ItemRowMapper());
	    if (items.size() > 1) {
	      throw new RuntimeException("Expected 1 result from findById(), instead found " + items.size()
	          + " (DB configuration error, maybe?)");
	    }
	    if (!items.isEmpty()) {
	      ret = items.get(0);
	    }
	    return ret;
	}
	
	public Notes findByAuthor(String name) {
	    Notes ret = null;
	    String sql = "SELECT * FROM " + Notes.TABLE_NAME + " WHERE autor = ?";
	    Object[] paramValues = { name };
	    JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
	    List<Notes> items = jdbc.query(sql, paramValues, new ItemRowMapper());
	    if (items.size() > 1) {
	      throw new RuntimeException("Expected 1 result from findByName(), instead found " + items.size()
	          + " (DB configuration error, maybe?)");
	    }
	    if (!items.isEmpty()) {
	      ret = items.get(0);
	    }
	    return ret;
	}
	
	public Notes findByTitle(String titulo) {
		Notes ret = null;
		String sql = "SELECT * FROM " + Notes.TABLE_NAME + " WHERE titulo = ?";
		Object[] paramValues = { titulo };
		JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
		List<Notes> items = jdbc.query(sql, paramValues, new ItemRowMapper());
	    if (items.size() > 1) {
	      throw new RuntimeException("Expected 1 result from findById(), instead found " + items.size()
	          + " (DB configuration error, maybe?)");
	    }
	    if (!items.isEmpty()) {
	      ret = items.get(0);
	    }
	    return ret;
	}
	
	public Notes add(Notes item) throws EntityPersistenceException {
	    Notes ret = null;
	    //
	    String sql = "INSERT INTO " + Notes.TABLE_NAME + "(titulo, autor, contenido, fecha) VALUES(?, ?, ?, ?)";
	    Object[] paramValues = {
	        item.get_Titulo(),
	        item.get_Autor(),
	        item.get_Contenido(),
	        item.get_Fecha()
	    };
	    JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
	    try {
	      int numRowsAffected = jdbc.update(sql, paramValues);
	      if (numRowsAffected == 1) {
	        ret = findByTitle(item.get_Titulo());
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
	
	public boolean update(Notes item) {
	    boolean ret = false;
	    //
	    String sql = "UPDATE " + Notes.TABLE_NAME + " SET titulo=?, autor=?, contenido=?, fecha=? WHERE id=?";
	    Object[] paramValues = {
	        item.get_Titulo(),
	        item.get_Autor(),
	        item.get_Contenido(),
	        item.get_Fecha(),
	    	item.get_Id()
	    };
	    JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
	    try {
	      log.info("Attempting to update " + Notes.TABLE_NAME + " table with object "
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
	
	public Boolean delete(Long id) throws EntityPersistenceException {
	    Boolean ret = false;
	    //
	    String sql = "DELETE FROM " + Notes.TABLE_NAME + " WHERE id = ?";
	    Object[] paramValues = {id};
	    JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
	    try {
	      int numRowsAffected = jdbc.update(sql, paramValues);
	      if (numRowsAffected != 1) {
	        String message = "Expected 1 row to be affected by DELETE, instead " + numRowsAffected
	            + " were affected (bad ID, maybe?)";
	        log.error(message);
	        throw new EntityPersistenceException(message);
	      }
	      ret = true;
	    } catch (DataAccessException e) {
	      String message = "Exception occurred while inserting record";
	      log.error(message, e);
	      throw new EntityPersistenceException(e);
	    }
	    return ret;
	}
	
	public class ItemRowMapper implements RowMapper<Notes> {
	    @Override
	    public Notes mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
	      return new Notes()
	    	  .set_Id(resultSet.getLong("id"))  
	          .set_Titulo(resultSet.getString("titulo"))
	          .set_Autor(resultSet.getString("autor"))
	          .set_Contenido(resultSet.getString("contenido"))
	      	  .set_Fecha(resultSet.getDate("fecha"));
	    }
	}	

}
