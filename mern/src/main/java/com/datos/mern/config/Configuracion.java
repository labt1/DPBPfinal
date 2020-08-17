package com.datos.mern.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.datos.mern")
public abstract class Configuracion implements databaseConfiguration{}
