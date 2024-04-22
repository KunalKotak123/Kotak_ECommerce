package com.kotak.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
		scanBasePackages = {"com.kotak"},
		exclude = {DataSourceAutoConfiguration.class})
public class inventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(inventoryServiceApplication.class, args);
	}

}
