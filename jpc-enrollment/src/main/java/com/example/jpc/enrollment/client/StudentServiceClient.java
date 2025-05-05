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
public class StudentServiceClient {

    private static final String STUDENT_SERVICE = "studentService";
    private static final String STUDENT_BASE_URL = "http://jpc-student-service/api/students/";

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(StudentServiceClient.class);


    @CircuitBreaker(name = STUDENT_SERVICE, fallbackMethod = "getStudentByIdFallback")
    @TimeLimiter(name = STUDENT_SERVICE)
    @Retry(name = STUDENT_SERVICE)
    public CompletableFuture<Map<String, Object>> getStudentById(Long studentId) {
        log.info("Llamando al servicio de estudiantes para obtener el estudiante con ID: {}", studentId);

        // La respuesta dependerá de la estructura de tu entidad Student
        Map<String, Object> student = restTemplate.getForObject(STUDENT_BASE_URL + studentId, Map.class);

        return CompletableFuture.completedFuture(student);
    }

    public CompletableFuture<Map<String, Object>> getStudentByIdFallback(Long studentId, Throwable t) {
        log.error("Circuit Breaker activado para la llamada al servicio de estudiantes. Error: {}", t.getMessage());

        // Creamos un objeto con datos temporales/alternativos
        Map<String, Object> fallbackStudent = new HashMap<>();
        fallbackStudent.put("id", studentId);
        fallbackStudent.put("name", "Información del estudiante temporalmente no disponible");
        fallbackStudent.put("email", "no-disponible@ejemplo.com");
        fallbackStudent.put("status", "CIRCUIT_BREAKER_FALLBACK");

        return CompletableFuture.completedFuture(fallbackStudent);
    }
}