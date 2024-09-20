package com.curso.ecomerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//excliuye la conexion a base de datos
@SpringBootApplication //(exclude = DataSourceAutoConfiguration.class)
public class EcomercePracticaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomercePracticaApplication.class, args);
	}

}
