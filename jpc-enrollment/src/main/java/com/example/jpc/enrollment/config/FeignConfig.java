package com.example.jpc.enrollment.config;

//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableFeignClients(basePackages = "com.example.jpc.enrollment.client")
//@EnableCircuitBreaker
public class FeignConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

// application.properties (o application.yml) añadir lo siguiente:
/*
# Habilitar fallbacks para Feign
feign.circuitbreaker.enabled=true

# Propiedades para resilience4j
resilience4j.circuitbreaker.instances.default.registerHealthIndicator=true
resilience4j.circuitbreaker.instances.default.slidingWindowSize=10
resilience4j.circuitbreaker.instances.default.minimumNumberOfCalls=5
resilience4j.circuitbreaker.instances.default.permittedNumberOfCallsInHalfOpenState=3
resilience4j.circuitbreaker.instances.default.automaticTransitionFromOpenToHalfOpenEnabled=true
resilience4j.circuitbreaker.instances.default.waitDurationInOpenState=5s
resilience4j.circuitbreaker.instances.default.failureRateThreshold=50
resilience4j.circuitbreaker.instances.default.eventConsumerBufferSize=10

# Configuraciones específicas para servicios
resilience4j.circuitbreaker.instances.courseService.baseConfig=default
resilience4j.circuitbreaker.instances.studentService.baseConfig=default

# Retry configuration
resilience4j.retry.instances.default.maxAttempts=3
resilience4j.retry.instances.default.waitDuration=1s
resilience4j.retry.instances.default.enableExponentialBackoff=true
resilience4j.retry.instances.default.exponentialBackoffMultiplier=2

# Timeout configuration
resilience4j.timelimiter.instances.default.timeoutDuration=2s
resilience4j.timelimiter.instances.courseService.timeoutDuration=1s
resilience4j.timelimiter.instances.studentService.timeoutDuration=1s
*/