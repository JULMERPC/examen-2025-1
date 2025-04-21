package com.example.jpc.student.service.impl;

import com.example.jpc.student.service.StudentService;
import com.example.jpc.student.dto.StudentDTO;
import com.example.jpc.student.entity.Student;
import com.example.jpc.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public StudentDTO createStudent(StudentDTO studentDTO) {
        if (existsByDocumento(studentDTO.getDocumento())) {
            throw new RuntimeException("El documento ya existe");
        }
        Student student = convertToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    @Override
    @Transactional
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Verificar si el documento está siendo usado por otro estudiante
        if (!student.getDocumento().equals(studentDTO.getDocumento()) &&
                existsByDocumento(studentDTO.getDocumento())) {
            throw new RuntimeException("El documento ya existe");
        }

        student.setNombre(studentDTO.getNombre());
        student.setCarrera(studentDTO.getCarrera());
        student.setEstado(studentDTO.getEstado());
        student.setCicloActual(studentDTO.getCicloActual());
        student.setDocumento(studentDTO.getDocumento());

        Student updatedStudent = studentRepository.save(student);
        return convertToDTO(updatedStudent);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return convertToDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Estudiante no encontrado");
        }
        studentRepository.deleteById(id);
    }

    @Override
    public boolean existsByDocumento(String documento) {
        return studentRepository.existsByDocumento(documento);
    }

    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setNombre(student.getNombre());
        dto.setCarrera(student.getCarrera());
        dto.setEstado(student.getEstado());
        dto.setCicloActual(student.getCicloActual());
        dto.setDocumento(student.getDocumento());
        return dto;
    }

    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setNombre(dto.getNombre());
        student.setCarrera(dto.getCarrera());
        student.setEstado(dto.getEstado());
        student.setCicloActual(dto.getCicloActual());
        student.setDocumento(dto.getDocumento());
        return student;
    }
}