package com.example.jpc.student.service;

import com.example.jpc.student.dto.StudentDTO;
import java.util.List;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);
    StudentDTO getStudentById(Long id);
    List<StudentDTO> getAllStudents();
    void deleteStudent(Long id);
    boolean existsByDocumento(String documento);
}