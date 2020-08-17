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
import com.datos.mern.model.User;
import com.mern.ws.Controlador;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/UserRestService")
public class UserRestService extends Controlador {
	  @RequestMapping("/FindAll")
	  public List<User> findAllItems() {
	    return getUserService().findAll();
	  }

	@RequestMapping("/FindById/{id}")
	public User findById(@PathVariable Long id) {
		return getUserService().findById(id);
	}
	
    @RequestMapping("/FindByName/{name}")
    public User findByName(@PathVariable String name) {
     return getUserService().findByName(name);
    }
	
	@RequestMapping(value = "/Add", method = RequestMethod.PUT)
	public User add(@RequestBody User item) {
    User ret;
    try {
      ret = getUserService().add(item);
    } catch (ServiceException e) {
      throw new RuntimeException("Could not add Item: " + ReflectionToStringBuilder.toString(item), e);
    }
    return ret;
    }
	
  @RequestMapping(value = "/Update", method = RequestMethod.POST)
  public String update(@RequestBody User item) {
    String ret = "UPDATE FAILED";
    try {
      boolean updated = getUserService().update(item);
      if (updated) {
        ret = "UPDATE SUCCESSFUL";
      }
    } catch (ServiceException e) {
      throw new RuntimeException("Could not update Item: " + ReflectionToStringBuilder.toString(item), e);
    }
    return ret;
  }
	  
}