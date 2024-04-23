package com.kotak.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.kotak"})
public class inventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(inventoryServiceApplication.class, args);
	}

}
