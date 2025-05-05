package com.example.jpc.enrollment.client;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
//@Slf4j
public class CourseServiceClient {
//
//    private static final String COURSE_SERVICE = "courseService";
//    private static final String COURSE_BASE_URL = "http://jpc-course-service/api/courses/";
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private static final Logger log = LoggerFactory.getLogger(CourseServiceClient.class);
//
//    @CircuitBreaker(name = COURSE_SERVICE, fallbackMethod = "getCourseByIdFallback")
//    @TimeLimiter(name = COURSE_SERVICE)
//    @Retry(name = COURSE_SERVICE)
//    public CompletableFuture<Map<String, Object>> getCourseById(Long courseId) {
//        log.info("Llamando al servicio de cursos para obtener el curso con ID: {}", courseId);
//
//        // La respuesta dependerá de la estructura de tu entidad Course
//        Map<String, Object> course = restTemplate.getForObject(COURSE_BASE_URL + courseId, Map.class);
//
//        return CompletableFuture.completedFuture(course);
//    }
//
//    public CompletableFuture<Map<String, Object>> getCourseByIdFallback(Long courseId, Throwable t) {
//        log.error("Circuit Breaker activado para la llamada al servicio de cursos. Error: {}", t.getMessage());
//
//        // Creamos un objeto con datos temporales/alternativos
//        Map<String, Object> fallbackCourse = new HashMap<>();
//        fallbackCourse.put("id", courseId);
//        fallbackCourse.put("name", "Información del curso temporalmente no disponible");
//        fallbackCourse.put("credits", 0);
//        fallbackCourse.put("status", "CIRCUIT_BREAKER_FALLBACK");
//
//        return CompletableFuture.completedFuture(fallbackCourse);
//    }
}