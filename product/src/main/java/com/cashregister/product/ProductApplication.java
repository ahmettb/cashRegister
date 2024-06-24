package com.cashregister.product;

import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProductApplication.class, args);

		


	}

}
