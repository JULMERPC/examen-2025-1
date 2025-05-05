
package com.example.jpc.enrollment.service;

//import com.example.jpc.enrollment.client.CourseClient;
//import com.example.jpc.enrollment.client.StudentClient;
//import com.example.jpc.enrollment.dto.CourseDTO;
//import com.example.jpc.enrollment.dto.EnrollmentDTO;
//import com.example.jpc.enrollment.dto.StudentDTO;
//import com.example.jpc.enrollment.entity.Enrollment;
//import com.example.jpc.enrollment.repository.EnrollmentRepository;
//import feign.FeignException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class EnrollmentService {
//
//    @Autowired
//    private EnrollmentRepository enrollmentRepository;
//
//
//    @Autowired
//    private StudentClient studentClient;
//
//    @Autowired
//    private CourseClient courseClient;
//
//    public List<EnrollmentDTO> getAllEnrollments() {
//        return enrollmentRepository.findAll().stream()
//                .map(this::convertToDTOWithDetails)
//                .collect(Collectors.toList());
//    }
//
//    public EnrollmentDTO getEnrollmentById(Long id) {
//        Enrollment enrollment = enrollmentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
//        return convertToDTOWithDetails(enrollment);
//    }
//
//    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
//        try {
//            // Verify student exists and is active
//            StudentDTO student = studentClient.getStudentById(enrollmentDTO.getStudentId());
//            if (!"active".equalsIgnoreCase(student.getStatus())) {
//                throw new RuntimeException("El estudiante no esta activo");
//            }
//
//            // Verify course exists and has capacity
//            CourseDTO course = courseClient.getCourseById(enrollmentDTO.getCourseId());
//            if (course.getCapacity() <= 0) {
//                throw new RuntimeException("el curso a llegado a su maxima capacidad asi que fuera chibolo");
//            }
//
//            // Check if student is already enrolled in the course
//            if (enrollmentRepository.existsByStudentIdAndCourseId(enrollmentDTO.getStudentId(), enrollmentDTO.getCourseId())) {
//                throw new RuntimeException("Student is already enrolled in this course");
//            }
//
//            // Set cycle from course
//            enrollmentDTO.setCycle(course.getCycle());
//
//            // Decrease course capacity
//            courseClient.decreaseCapacity(enrollmentDTO.getCourseId());
//
//            Enrollment enrollment = convertToEntity(enrollmentDTO);
//            return convertToDTOWithDetails(enrollmentRepository.save(enrollment));
//
//        } catch (FeignException e) {
//            throw new RuntimeException("Error while communicating with other services: " + e.getMessage());
//        }
//    }
//
//    public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO enrollmentDTO) {
//        Enrollment existingEnrollment = enrollmentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
//
//        try {
//            // Verify student exists and is active
//            StudentDTO student = studentClient.getStudentById(enrollmentDTO.getStudentId());
//            if (!"active".equalsIgnoreCase(student.getStatus())) {
//                throw new RuntimeException("Student is not active");
//            }
//
//            // Verify course exists and has capacity (only if changing the course)
//            if (!existingEnrollment.getCourseId().equals(enrollmentDTO.getCourseId())) {
//                CourseDTO course = courseClient.getCourseById(enrollmentDTO.getCourseId());
//                Long currentEnrollments = enrollmentRepository.countByCourseId(enrollmentDTO.getCourseId());
//                if (currentEnrollments >= course.getCapacity()) {
//                    throw new RuntimeException("Course has reached its maximum capacity");
//                }
//
//                // Check if student is already enrolled in the course
//                if (enrollmentRepository.existsByStudentIdAndCourseId(enrollmentDTO.getStudentId(), enrollmentDTO.getCourseId())) {
//                    throw new RuntimeException("Student is already enrolled in this course");
//                }
//
//                // Set cycle from course
//                enrollmentDTO.setCycle(course.getCycle());
//            }
//
//            existingEnrollment.setStudentId(enrollmentDTO.getStudentId());
//            existingEnrollment.setCourseId(enrollmentDTO.getCourseId());
//            existingEnrollment.setCycle(enrollmentDTO.getCycle());
//
//            return convertToDTOWithDetails(enrollmentRepository.save(existingEnrollment));
//
//        } catch (FeignException e) {
//            throw new RuntimeException("Error while communicating with other services: " + e.getMessage());
//        }
//    }
//
//    public void deleteEnrollment(Long id) {
//        if (!enrollmentRepository.existsById(id)) {
//            throw new RuntimeException("Enrollment not found with id: " + id);
//        }
//        enrollmentRepository.deleteById(id);
//    }
//
//    public List<EnrollmentDTO> getEnrollmentsByStudentId(Long studentId) {
//        return enrollmentRepository.findByStudentId(studentId).stream()
//                .map(this::convertToDTOWithDetails)
//                .collect(Collectors.toList());
//    }
//
//    public List<EnrollmentDTO> getEnrollmentsByCourseId(Long courseId) {
//        return enrollmentRepository.findByCourseId(courseId).stream()
//                .map(this::convertToDTOWithDetails)
//                .collect(Collectors.toList());
//    }
//
//    private EnrollmentDTO convertToDTOWithDetails(Enrollment enrollment) {
//        EnrollmentDTO dto = new EnrollmentDTO();
//        dto.setId(enrollment.getId());
//        dto.setStudentId(enrollment.getStudentId());
//        dto.setCourseId(enrollment.getCourseId());
//        dto.setCycle(enrollment.getCycle());
//        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
//
//        try {
//            // Get student details
//            StudentDTO student = studentClient.getStudentById(enrollment.getStudentId());
//            dto.setStudentName(student.getName());
//
//            // Get course details
//            CourseDTO course = courseClient.getCourseById(enrollment.getCourseId());
//            dto.setCourseName(course.getName());
//        } catch (FeignException e) {
//            // Handle case where services are not available
//            dto.setStudentName("Service unavailable");
//            dto.setCourseName("Service unavailable");
//        }
//
//        return dto;
//    }
//
//    private Enrollment convertToEntity(EnrollmentDTO dto) {
//        Enrollment enrollment = new Enrollment();
//        enrollment.setId(dto.getId());
//        enrollment.setStudentId(dto.getStudentId());
//        enrollment.setCourseId(dto.getCourseId());
//        enrollment.setCycle(dto.getCycle());
//        return enrollment;
//    }
//}









import com.example.jpc.enrollment.client.CourseClient;
import com.example.jpc.enrollment.client.StudentClient;
import com.example.jpc.enrollment.dto.CourseDTO;
import com.example.jpc.enrollment.dto.EnrollmentDTO;
import com.example.jpc.enrollment.dto.StudentDTO;
import com.example.jpc.enrollment.entity.Enrollment;
import com.example.jpc.enrollment.global.GlobalExceptionHandler;
import com.example.jpc.enrollment.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@Slf4j
public class EnrollmentService {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentClient studentClient;

    @Autowired
    private CourseClient courseClient;

    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(this::convertToDTOWithDetails)
                .collect(Collectors.toList());
    }

    public EnrollmentDTO getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
        return convertToDTOWithDetails(enrollment);
    }

    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        log.info("Creando inscripción para estudiante ID: {} y curso ID: {}",
                enrollmentDTO.getStudentId(), enrollmentDTO.getCourseId());

        // Verify student exists and is active
        StudentDTO student = studentClient.getStudentById(enrollmentDTO.getStudentId());
        if (student.getStatus() != null && student.getStatus().contains("CIRCUIT_BREAKER_FALLBACK")) {
            log.error("Servicio de estudiantes no disponible, activado circuit breaker");
            throw new RuntimeException("Servicio de estudiantes temporalmente no disponible");
        }

        if (!"active".equalsIgnoreCase(student.getStatus())) {
            throw new RuntimeException("El estudiante no está activo");
        }

        // Verify course exists and has capacity
        CourseDTO course = courseClient.getCourseById(enrollmentDTO.getCourseId());
        if (course.getName() != null && course.getName().contains("temporalmente no disponible")) {
            log.error("Servicio de cursos no disponible, activado circuit breaker");
            throw new RuntimeException("Servicio de cursos temporalmente no disponible");
        }

        if (course.getCapacity() <= 0) {
            throw new RuntimeException("El curso ha llegado a su máxima capacidad");
        }

        // Check if student is already enrolled in the course
        if (enrollmentRepository.existsByStudentIdAndCourseId(enrollmentDTO.getStudentId(), enrollmentDTO.getCourseId())) {
            throw new RuntimeException("El estudiante ya está inscrito en este curso");
        }

        // Set cycle from course
        enrollmentDTO.setCycle(course.getCycle());

        // Decrease course capacity
        CourseDTO updatedCourse = courseClient.decreaseCapacity(enrollmentDTO.getCourseId());
        if (updatedCourse.getCapacity() < 0) {
            log.error("No se pudo actualizar la capacidad del curso, posible circuit breaker");
            throw new RuntimeException("No se pudo actualizar la capacidad del curso");
        }

        Enrollment enrollment = convertToEntity(enrollmentDTO);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        log.info("Inscripción creada exitosamente con ID: {}", savedEnrollment.getId());

        return convertToDTOWithDetails(savedEnrollment);
    }

    public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO enrollmentDTO) {
        log.info("Actualizando inscripción ID: {}", id);

        Enrollment existingEnrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));

        // Verify student exists and is active
        StudentDTO student = studentClient.getStudentById(enrollmentDTO.getStudentId());
        if (student.getStatus() != null && student.getStatus().contains("CIRCUIT_BREAKER_FALLBACK")) {
            log.error("Servicio de estudiantes no disponible, activado circuit breaker");
            throw new RuntimeException("Servicio de estudiantes temporalmente no disponible");
        }

        if (!"active".equalsIgnoreCase(student.getStatus())) {
            throw new RuntimeException("El estudiante no está activo");
        }

        // Verify course exists and has capacity (only if changing the course)
        if (!existingEnrollment.getCourseId().equals(enrollmentDTO.getCourseId())) {
            CourseDTO course = courseClient.getCourseById(enrollmentDTO.getCourseId());
            if (course.getName() != null && course.getName().contains("temporalmente no disponible")) {
                log.error("Servicio de cursos no disponible, activado circuit breaker");
                throw new RuntimeException("Servicio de cursos temporalmente no disponible");
            }

            if (course.getCapacity() <= 0) {
                throw new RuntimeException("El curso ha llegado a su máxima capacidad");
            }

            // Check if student is already enrolled in the course
            if (enrollmentRepository.existsByStudentIdAndCourseId(enrollmentDTO.getStudentId(), enrollmentDTO.getCourseId())) {
                throw new RuntimeException("El estudiante ya está inscrito en este curso");
            }

            // Set cycle from course
            enrollmentDTO.setCycle(course.getCycle());
        }

        existingEnrollment.setStudentId(enrollmentDTO.getStudentId());
        existingEnrollment.setCourseId(enrollmentDTO.getCourseId());
        existingEnrollment.setCycle(enrollmentDTO.getCycle());

        Enrollment updatedEnrollment = enrollmentRepository.save(existingEnrollment);
        log.info("Inscripción actualizada exitosamente");

        return convertToDTOWithDetails(updatedEnrollment);
    }

    public void deleteEnrollment(Long id) {
        log.info("Eliminando inscripción ID: {}", id);

        if (!enrollmentRepository.existsById(id)) {
            throw new RuntimeException("Inscripción no encontrada con id: " + id);
        }
        enrollmentRepository.deleteById(id);
        log.info("Inscripción eliminada exitosamente");
    }

    public List<EnrollmentDTO> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTOWithDetails)
                .collect(Collectors.toList());
    }

    public List<EnrollmentDTO> getEnrollmentsByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(this::convertToDTOWithDetails)
                .collect(Collectors.toList());
    }

    private EnrollmentDTO convertToDTOWithDetails(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(enrollment.getId());
        dto.setStudentId(enrollment.getStudentId());
        dto.setCourseId(enrollment.getCourseId());
        dto.setCycle(enrollment.getCycle());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());

        // Get student details
        StudentDTO student = studentClient.getStudentById(enrollment.getStudentId());
        dto.setStudentName(student.getName());

        // Get course details
        CourseDTO course = courseClient.getCourseById(enrollment.getCourseId());
        dto.setCourseName(course.getName());

        return dto;
    }

    private Enrollment convertToEntity(EnrollmentDTO dto) {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(dto.getId());
        enrollment.setStudentId(dto.getStudentId());
        enrollment.setCourseId(dto.getCourseId());
        enrollment.setCycle(dto.getCycle());
        return enrollment;
    }
}