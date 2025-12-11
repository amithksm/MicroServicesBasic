package com.ak.microservice.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import io.github.resilience4j.retry.annotation.Retry;

@Service
public class HelloService {
	
	private final Random rand = new Random();
	
	@Retry(name = "amithRetry", fallbackMethod = "amithFallBack")
	public String getHello() {
		if(rand.nextBoolean()) {
			return "Boolean TRUE: api success.";
		}else {
			System.out.println("Boolean FALSE: api failed...");
			throw new RuntimeException("Boolean FALSE: api failed");
		}
	}
	
	public String amithFallBack(Throwable ex) {
		System.out.println("Inside fallback - amithFallBack()");
		return ex.getMessage();
	}

}
