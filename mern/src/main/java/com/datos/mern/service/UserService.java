package com.datos.mern.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datos.mern.exception.EntityPersistenceException;
import com.datos.mern.exception.ServiceException;
import com.datos.mern.dao.UserDAO;
import com.datos.mern.model.User;

@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	static final String NOT_INITIALIZED_MESSAGE = "Item DAO has not been initialized, cannot continue.";
	
	  @Autowired
	  private UserDAO itemDao;

	  UserDAO getItemDao() {
	    if (itemDao == null) {
	      throw new RuntimeException(NOT_INITIALIZED_MESSAGE);
	    }
	    return itemDao;
	  }
	  
	  public void setItemDao(UserDAO itemDao) {
		    this.itemDao = itemDao;
	  }
	  
	  public List<User> findAll() {
		    return getItemDao().findAll();
	  }
	  
	  public User findById(Long id) {
		    return getItemDao().findById(id);
	  }
	  
	  public User findByName(String name) {
		    return getItemDao().findByName(name);
	  }
	  
	  public User findByDescription(String description) {
		    return getItemDao().findByDescription(description);
	  }
	  
	  public User add(User item) throws ServiceException {
		    User ret;
		    try {
		      ret = getItemDao().add(item);
		    } catch (EntityPersistenceException e) {
		      String message = "Exception thrown while adding Category object";
		      log.error(message, e);
		      throw new ServiceException(message, e);
		    }
		    return ret;
	  }
	  
	  public boolean update(User item) throws ServiceException {
		    boolean ret;
		    ret = getItemDao().update(item);
		    if (ret == false) {
		      String message = "Update FAILED";
		      log.error(message);
		      throw new ServiceException(message);
		    }
		    return ret;
	  }
	  
	  public User delete(User item) throws ServiceException {
		    User ret;
		    try {
		      ret = getItemDao().delete(item);
		    } catch (EntityPersistenceException e) {
		      String message = "Exception thrown while deleting Category object";
		      log.error(message, e);
		      throw new ServiceException(message, e);
		    }
		    return ret;
	  }
	
}
