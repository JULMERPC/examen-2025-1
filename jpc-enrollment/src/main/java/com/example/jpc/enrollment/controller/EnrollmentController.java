package com.example.jpc.enrollment.controller;

//import com.example.jpc.enrollment.dto.EnrollmentDTO;
//import com.example.jpc.enrollment.service.EnrollmentService;
//import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//import java.util.HashMap;
//
//@RestController
//@RequestMapping("/api/enrollments")
//@RequiredArgsConstructor
//public class EnrollmentController {
//
//    @Autowired
//    private EnrollmentService enrollmentService;
//
//    @GetMapping
//    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
//        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable Long id) {
//        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
//    }
//
//    @PostMapping
//    public ResponseEntity<EnrollmentDTO> createEnrollment(@Valid @RequestBody EnrollmentDTO enrollmentDTO) {
//        return new ResponseEntity<>(enrollmentService.createEnrollment(enrollmentDTO), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<EnrollmentDTO> updateEnrollment(@PathVariable Long id,
//                                                          @Valid @RequestBody EnrollmentDTO enrollmentDTO) {
//        return ResponseEntity.ok(enrollmentService.updateEnrollment(id, enrollmentDTO));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
//        enrollmentService.deleteEnrollment(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/student/{studentId}")
//    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByStudentId(@PathVariable Long studentId) {
//        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudentId(studentId));
//    }
//
//    @GetMapping("/course/{courseId}")
//    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByCourseId(@PathVariable Long courseId) {
//        return ResponseEntity.ok(enrollmentService.getEnrollmentsByCourseId(courseId));
//    }
//
//
//}











//
//public class EnrollmentController {
//
//    @Autowired
//    private EnrollmentService enrollmentService;
//    private Logger log;
//
//    @GetMapping
//    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
//        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable Long id) {
//        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
//    }
//
//    @PostMapping
//    public ResponseEntity<EnrollmentDTO> createEnrollment(@Valid @RequestBody EnrollmentDTO enrollmentDTO) {
//        try {
//            return new ResponseEntity<>(enrollmentService.createEnrollment(enrollmentDTO), HttpStatus.CREATED);
//        } catch (CallNotPermittedException e) {
//            log.error("Circuit breaker abierto al crear inscripción: {}", e.getMessage());
//            throw e; // El GlobalExceptionHandler manejará esta excepción
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<EnrollmentDTO> updateEnrollment(@PathVariable Long id,
//                                                          @Valid @RequestBody EnrollmentDTO enrollmentDTO) {
//        try {
//            return ResponseEntity.ok(enrollmentService.updateEnrollment(id, enrollmentDTO));
//        } catch (CallNotPermittedException e) {
//            log.error("Circuit breaker abierto al actualizar inscripción: {}", e.getMessage());
//            throw e; // El GlobalExceptionHandler manejará esta excepción
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
//        enrollmentService.deleteEnrollment(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/student/{studentId}")
//    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByStudentId(@PathVariable Long studentId) {
//        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudentId(studentId));
//    }
//
//    @GetMapping("/course/{courseId}")
//    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByCourseId(@PathVariable Long courseId) {
//        return ResponseEntity.ok(enrollmentService.getEnrollmentsByCourseId(courseId));
//    }
//
//    // Añadir un endpoint para verificar el estado del circuit breaker
//    @GetMapping("/health")
//    public ResponseEntity<Map<String, String>> checkHealth() {
//        Map<String, String> status = new HashMap<>();
//        status.put("status", "UP");
//        status.put("message", "El servicio de inscripciones está funcionando correctamente");
//        return ResponseEntity.ok(status);
//    }
//}










import com.example.jpc.enrollment.dto.EnrollmentDTO;
import com.example.jpc.enrollment.global.GlobalExceptionHandler;
import com.example.jpc.enrollment.service.EnrollmentService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
//@Slf4j
public class EnrollmentController {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        log.info("Obteniendo todas las inscripciones");
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable Long id) {
        log.info("Obteniendo inscripción con ID: {}", id);
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
    }

    @PostMapping
    public ResponseEntity<?> createEnrollment(@Valid @RequestBody EnrollmentDTO enrollmentDTO) {
        try {
            log.info("Creando nueva inscripción");
            EnrollmentDTO created = enrollmentService.createEnrollment(enrollmentDTO);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (CallNotPermittedException e) {
            log.error("Circuit breaker abierto al crear inscripción: {}", e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("error", "Servicio temporalmente no disponible");
            response.put("message", "Circuit breaker abierto. Intente más tarde.");
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (RuntimeException e) {
            log.error("Error al crear inscripción: {}", e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al procesar la solicitud");
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnrollment(@PathVariable Long id,
                                              @Valid @RequestBody EnrollmentDTO enrollmentDTO) {
        try {
            log.info("Actualizando inscripción con ID: {}", id);
            EnrollmentDTO updated = enrollmentService.updateEnrollment(id, enrollmentDTO);
            return ResponseEntity.ok(updated);
        } catch (CallNotPermittedException e) {
            log.error("Circuit breaker abierto al actualizar inscripción: {}", e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("error", "Servicio temporalmente no disponible");
            response.put("message", "Circuit breaker abierto. Intente más tarde.");
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (RuntimeException e) {
            log.error("Error al actualizar inscripción: {}", e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al procesar la solicitud");
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        log.info("Eliminando inscripción con ID: {}", id);
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        log.info("Obteniendo inscripciones para el estudiante con ID: {}", studentId);
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudentId(studentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByCourseId(@PathVariable Long courseId) {
        log.info("Obteniendo inscripciones para el curso con ID: {}", courseId);
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByCourseId(courseId));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> checkHealth() {
        log.info("Verificando estado del servicio");
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("message", "El servicio de inscripciones está funcionando correctamente");
        return ResponseEntity.ok(status);
    }
}