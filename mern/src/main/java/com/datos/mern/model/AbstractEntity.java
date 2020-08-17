package com.datos.mern.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public abstract class AbstractEntity {
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private Long id;
	  private Date fecha;
	  
	  public Long get_Id() {
		    return id;
		  }

		  AbstractEntity set_Id(Long id) {
		    this.id = id;
		    return this;
		  }

		  public Date get_Fecha() {
		    return fecha;
		  }

		  public AbstractEntity set_Fecha(Date fecha) {
		    this.fecha = fecha;
		    return this;
		  }
}
