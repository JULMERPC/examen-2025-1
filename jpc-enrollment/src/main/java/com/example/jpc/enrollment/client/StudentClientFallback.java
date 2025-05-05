package com.example.jpc.enrollment.client;


import com.example.jpc.enrollment.dto.StudentDTO;
import org.springframework.stereotype.Component;

@Component
public class StudentClientFallback implements StudentClient {

    @Override
    public StudentDTO getStudentById(Long id) {
        StudentDTO fallbackStudent = new StudentDTO();
        fallbackStudent.setId(id);
        fallbackStudent.setName("Información del estudiante temporalmente no disponible");
        fallbackStudent.setDocumentNumber("UNAVAILABLE");
        fallbackStudent.setCareer("UNAVAILABLE");
        fallbackStudent.setStatus("CIRCUIT_BREAKER_FALLBACK");
        fallbackStudent.setCurrentCycle(0);
        return fallbackStudent;
    }
}