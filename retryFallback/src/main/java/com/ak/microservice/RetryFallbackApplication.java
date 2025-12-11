package com.ak.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RetryFallbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetryFallbackApplication.class, args);
	}

}
