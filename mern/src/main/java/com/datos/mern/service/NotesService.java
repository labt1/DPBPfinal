package com.datos.mern.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datos.mern.dao.NotesDAO;
import com.datos.mern.exception.EntityPersistenceException;
import com.datos.mern.exception.ServiceException;
import com.datos.mern.model.Notes;

@Service

public class NotesService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	static final String NOT_INITIALIZED_MESSAGE = "Item DAO has not been initialized, cannot continue.";
	
	  @Autowired
	  private NotesDAO itemDao;

	  NotesDAO getItemDao() {
	    if (itemDao == null) {
	      throw new RuntimeException(NOT_INITIALIZED_MESSAGE);
	    }
	    return itemDao;
	  }
	  
	  public void setItemDao(NotesDAO itemDao) {
		    this.itemDao = itemDao;
	  }
	  
	  public List<Notes> findAll() {
		    return getItemDao().findAll();
	  }
	  
	  public Notes findById(Long id) {
		    return getItemDao().findById(id);
	  }
	  
	  public Notes findByTitle(String title) {
		    return getItemDao().findByTitle(title);
	 }
	  
	  public Notes add(Notes item) throws ServiceException {
		  Notes ret;
		    try {
		      ret = getItemDao().add(item);
		    } catch (EntityPersistenceException e) {
		      String message = "Exception thrown while adding Category object";
		      log.error(message, e);
		      throw new ServiceException(message, e);
		    }
		    return ret;
	  }
	  
	  public boolean update(Notes item) throws ServiceException {
		    boolean ret;
		    ret = getItemDao().update(item);
		    if (ret == false) {
		      String message = "Update FAILED";
		      log.error(message);
		      throw new ServiceException(message);
		    }
		    return ret;
	  }
	  
	  public Boolean delete(Long id) throws ServiceException {
		  	Boolean ret;
		    try {
		      ret = getItemDao().delete(id);
		    } catch (EntityPersistenceException e) {
		      String message = "Exception thrown while deleting Category object";
		      log.error(message, e);
		      throw new ServiceException(message, e);
		    }
		    return ret;
	  }
}
