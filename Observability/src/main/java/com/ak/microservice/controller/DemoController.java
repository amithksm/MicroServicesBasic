package com.ak.microservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ak.microservice.service.ExternalServiceSimulator;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/api")
public class DemoController {
	
	private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
    private final ExternalServiceSimulator serviceSimulator;
    
    public DemoController(ExternalServiceSimulator serviceSimulator) {
        this.serviceSimulator = serviceSimulator;
    }
    
    @GetMapping("/call-service")
    @Timed(value = "api.call.service", description = "Time taken to call external service")
    public ResponseEntity<String> callService() {
        logger.info("Received request to call external service");
        
        try {
            String result = serviceSimulator.simulateExternalCall();
            logger.info("External service call completed successfully: {}", result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("External service call failed: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Service call failed: " + e.getMessage());
        }
    }

}
