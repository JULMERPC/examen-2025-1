package com.example.jpc.student.repository;

import com.example.jpc.student.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Integer id(Integer id);
}
