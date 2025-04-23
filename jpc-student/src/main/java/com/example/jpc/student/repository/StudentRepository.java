package com.example.jpc.student.repository;


import com.example.jpc.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByDocumentNumber(String documentNumber);
    Optional<Student> findByDocumentNumber(String documentNumber);
}