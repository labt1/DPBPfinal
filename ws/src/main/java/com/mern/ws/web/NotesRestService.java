package com.mern.ws.web;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.datos.mern.exception.ServiceException;
import com.datos.mern.model.Notes;
import com.mern.ws.Controlador;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/NotesRestService")
public class NotesRestService extends Controlador{
	  @RequestMapping("/FindAll")
	  public List<Notes> findAllItems() {
	    return getNotesService().findAll();
	  }
	  
	  @RequestMapping(value = "/Add", method = RequestMethod.PUT)
	  public Notes add(@RequestBody Notes item) {
		  Notes ret;
	    try {
	      ret = getNotesService().add(item);
	    } catch (ServiceException e) {
	      throw new RuntimeException("Could not add Item: " + ReflectionToStringBuilder.toString(item), e);
	    }
	    return ret;
	  }
	  
	  @RequestMapping(value = "/Update", method = RequestMethod.POST)
	  public String update(@RequestBody Notes item) {
	    String ret = "UPDATE FAILED";
	    try {
	      boolean updated = getNotesService().update(item);
	      if (updated) {
	        ret = "UPDATE SUCCESSFUL";
	      }
	    } catch (ServiceException e) {
	      throw new RuntimeException("Could not update Item: " + ReflectionToStringBuilder.toString(item), e);
	    }
	    return ret;
	  }
	  
	  @RequestMapping(value = "/Delete/{id}", method = RequestMethod.DELETE)
	  public String delete(@PathVariable Long id) {
	    String ret = "DELETE FAILED";
	    try {
	      getNotesService().delete(id);
	      ret = "DELETE SUCCESSFUL";
	    } catch (ServiceException e) {
	      throw new RuntimeException("Could not delete Item: " + ReflectionToStringBuilder.toString(id), e);
	    }
	    return ret;
	  }
	  
	  @RequestMapping("/FindById/{id}")
	  public Notes findById(@PathVariable Long id) {
	    return getNotesService().findById(id);
	  }
}
