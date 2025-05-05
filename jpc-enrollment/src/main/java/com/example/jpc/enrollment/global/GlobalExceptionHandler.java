package com.example.jpc.enrollment.global;


import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
//@Slf4j
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<Map<String, String>> handleCircuitBreakerException(CallNotPermittedException ex) {
        log.error("Circuit Breaker Exception: {}", ex.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("error", "Servicio temporalmente no disponible");
        response.put("message", "El servicio está experimentando problemas. Por favor, inténtelo más tarde.");
        response.put("status", "SERVICE_UNAVAILABLE");

        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        log.error("Runtime Exception: {}", ex.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("error", "Error en el procesamiento");
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        log.error("Excepción general: {}", ex.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("error", "Error interno del servidor");
        response.put("message", "Se ha producido un error inesperado. Por favor, contacte al administrador.");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}