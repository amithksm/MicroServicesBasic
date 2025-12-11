package com.ak.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ak.microservice.service.HelloService;

@RestController
@RequestMapping("/api")
public class HelloController {
	
	@Autowired
	private HelloService hs;
	
	@GetMapping("/hello")
	public String hello() {
		return hs.getHello();
	}
}
