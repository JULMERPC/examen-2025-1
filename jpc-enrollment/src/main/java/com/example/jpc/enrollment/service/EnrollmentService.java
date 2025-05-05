
package com.example.jpc.enrollment.service;

import com.example.jpc.enrollment.client.CourseClient;
import com.example.jpc.enrollment.client.StudentClient;
import com.example.jpc.enrollment.dto.CourseDTO;
import com.example.jpc.enrollment.dto.EnrollmentDTO;
import com.example.jpc.enrollment.dto.StudentDTO;
import com.example.jpc.enrollment.entity.Enrollment;
import com.example.jpc.enrollment.repository.EnrollmentRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

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
        try {
            // Verify student exists and is active
            StudentDTO student = studentClient.getStudentById(enrollmentDTO.getStudentId());
            if (!"active".equalsIgnoreCase(student.getStatus())) {
                throw new RuntimeException("El estudiante no esta activo");
            }

            // Verify course exists and has capacity
            CourseDTO course = courseClient.getCourseById(enrollmentDTO.getCourseId());
            if (course.getCapacity() <= 0) {
                throw new RuntimeException("el curso a llegado a su maxima capacidad asi que fuera chibolo");
            }

            // Check if student is already enrolled in the course
            if (enrollmentRepository.existsByStudentIdAndCourseId(enrollmentDTO.getStudentId(), enrollmentDTO.getCourseId())) {
                throw new RuntimeException("Student is already enrolled in this course");
            }

            // Set cycle from course
            enrollmentDTO.setCycle(course.getCycle());

            // Decrease course capacity
            courseClient.decreaseCapacity(enrollmentDTO.getCourseId());

            Enrollment enrollment = convertToEntity(enrollmentDTO);
            return convertToDTOWithDetails(enrollmentRepository.save(enrollment));

        } catch (FeignException e) {
            throw new RuntimeException("Error while communicating with other services: " + e.getMessage());
        }
    }

    public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO enrollmentDTO) {
        Enrollment existingEnrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));

        try {
            // Verify student exists and is active
            StudentDTO student = studentClient.getStudentById(enrollmentDTO.getStudentId());
            if (!"active".equalsIgnoreCase(student.getStatus())) {
                throw new RuntimeException("Student is not active");
            }

            // Verify course exists and has capacity (only if changing the course)
            if (!existingEnrollment.getCourseId().equals(enrollmentDTO.getCourseId())) {
                CourseDTO course = courseClient.getCourseById(enrollmentDTO.getCourseId());
                Long currentEnrollments = enrollmentRepository.countByCourseId(enrollmentDTO.getCourseId());
                if (currentEnrollments >= course.getCapacity()) {
                    throw new RuntimeException("Course has reached its maximum capacity");
                }

                // Check if student is already enrolled in the course
                if (enrollmentRepository.existsByStudentIdAndCourseId(enrollmentDTO.getStudentId(), enrollmentDTO.getCourseId())) {
                    throw new RuntimeException("Student is already enrolled in this course");
                }

                // Set cycle from course
                enrollmentDTO.setCycle(course.getCycle());
            }

            existingEnrollment.setStudentId(enrollmentDTO.getStudentId());
            existingEnrollment.setCourseId(enrollmentDTO.getCourseId());
            existingEnrollment.setCycle(enrollmentDTO.getCycle());

            return convertToDTOWithDetails(enrollmentRepository.save(existingEnrollment));

        } catch (FeignException e) {
            throw new RuntimeException("Error while communicating with other services: " + e.getMessage());
        }
    }

    public void deleteEnrollment(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new RuntimeException("Enrollment not found with id: " + id);
        }
        enrollmentRepository.deleteById(id);
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

        try {
            // Get student details
            StudentDTO student = studentClient.getStudentById(enrollment.getStudentId());
            dto.setStudentName(student.getName());

            // Get course details
            CourseDTO course = courseClient.getCourseById(enrollment.getCourseId());
            dto.setCourseName(course.getName());
        } catch (FeignException e) {
            // Handle case where services are not available
            dto.setStudentName("Service unavailable");
            dto.setCourseName("Service unavailable");
        }

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