package com.mern.ws;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.datos.mern.config.Configuracion;

@Configuration
public class WSConfiguracion extends Configuracion {
	  @Override
	  @Bean(name = "dataSource")
	  public DataSource getDataSource() {
	    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
	    EmbeddedDatabase embeddedDb = builder
	        .setType(EmbeddedDatabaseType.DERBY)
	        .ignoreFailedDrops(true)
	        .addScript("sql/create_tables.sql")
	        .addScript("sql/insert_data.sql")
	        .build();
	    return embeddedDb;
	  }
}
