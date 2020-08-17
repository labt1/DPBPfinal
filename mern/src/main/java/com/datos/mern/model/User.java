package com.datos.mern.model;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = User.TABLE_NAME)

public class User extends AbstractEntity{
	public static final String TABLE_NAME = "users";
	private String nombre;
	private String description;
	
	public User set_Nombre(String nombre){
		this.nombre=nombre;
		return this;
	}
	
	public User set_Descripcion(String description){
		this.description=description;
		return this;
	}
	
	public String get_Nombre() {
	    return nombre;
	}
	
	public String get_Description() {
	    return description;
	}
	
	  @Override
	  public User set_Id(Long id) {
	    super.set_Id(id);
	    return this;
	  }
}
