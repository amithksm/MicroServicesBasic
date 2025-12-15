package com.ak.microservice.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Service
public class ExternalServiceSimulator {
	
	private static final Logger logger = LoggerFactory.getLogger(ExternalServiceSimulator.class);
    private final Random random = new Random();
    
    private final Counter successCounter;
    private final Counter failureCounter;
    private final Counter timeoutCounter;
    private final Timer callTimer;
    
    public ExternalServiceSimulator(MeterRegistry meterRegistry) {
        this.successCounter = Counter.builder("external.service.calls")
                .tag("status", "success")
                .description("Number of successful external service calls")
                .register(meterRegistry);
        
        this.failureCounter = Counter.builder("external.service.calls")
                .tag("status", "failure")
                .description("Number of failed external service calls")
                .register(meterRegistry);
        
        this.timeoutCounter = Counter.builder("external.service.calls")
                .tag("status", "timeout")
                .description("Number of timed out external service calls")
                .register(meterRegistry);
        
        this.callTimer = Timer.builder("external.service.duration")
                .description("Duration of external service calls")
                .register(meterRegistry);
    }
    
    public String simulateExternalCall() {
        return callTimer.record(() -> {
            int outcome = random.nextInt(100);
            
            try {
                // Simulate network delay
                int delay = random.nextInt(2000) + 500; // 500ms to 2.5s
                Thread.sleep(delay);
                
                if (outcome < 60) {
                    // 60% success
                    logger.info("External service call succeeded after {}ms", delay);
                    successCounter.increment();
                    return "SUCCESS: Service responded with data after " + delay + "ms";
                    
                } else if (outcome < 85) {
                    // 25% failure
                    logger.warn("External service call failed after {}ms - Service returned error", delay);
                    failureCounter.increment();
                    throw new RuntimeException("External service returned 500 Internal Server Error");
                    
                } else {
                    // 15% timeout
                    logger.error("External service call timed out after {}ms", delay);
                    Thread.sleep(3000); // Simulate long timeout
                    timeoutCounter.increment();
                    throw new RuntimeException("External service timed out after 3000ms");
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Thread interrupted during service call", e);
                throw new RuntimeException("Service call interrupted", e);
            }
        });
    }

}
