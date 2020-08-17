package com.datos.mern.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = Notes.TABLE_NAME)
public class Notes extends AbstractEntity{
	  public static final String TABLE_NAME = "notes";
	  
	  private String titulo;
	  private String autor;
	  private String contenido;
	  
	  public Notes set_Contenido(String contenido) {
		    this.contenido = contenido;
		    return this;
	  }
	   
	  public Notes set_Titulo(String titulo) {
		    this.titulo = titulo;
		    return this;
	  }
	  
	  public Notes set_Autor(String autor) {
		    this.autor = autor;
		    return this;
	  }
	  
	  
	  public String get_Titulo() {
		  return titulo;
	  }
	  
	  public String get_Autor() {
		  return autor;
	  }
	  
	  public String get_Contenido() {
		  return contenido;
	  }
	  
	  @Override
	  public Notes set_Id(Long id) {
	    super.set_Id(id);
	    return this;
	  }
	  
	  @Override
	  public Notes set_Fecha(Date fecha) {
	    super.set_Fecha(fecha);
	    return this;
	  }
	  
}
