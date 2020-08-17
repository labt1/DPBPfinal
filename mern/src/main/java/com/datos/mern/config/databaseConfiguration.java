package com.datos.mern.config;

import javax.sql.DataSource;

public interface databaseConfiguration {
	public DataSource getDataSource();
}
