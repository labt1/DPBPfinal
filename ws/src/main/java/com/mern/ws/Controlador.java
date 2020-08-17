package com.mern.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.datos.mern.service.UserService;
import com.datos.mern.service.NotesService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class Controlador {
	  @Autowired
	  private UserService userService;

	  @Autowired
	  private NotesService notesService;

	  public UserService getUserService() {
	    if (userService == null) {
	      throw new RuntimeException("ItemService not configured. Cannot continue.");
	    }
	    return userService;
	  }

	  public NotesService getNotesService() {
	    if (notesService == null) {
	      throw new RuntimeException("CategoryService not configured. Cannot continue.");
	    }
	    return notesService;
	  }
}
