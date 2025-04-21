package com.example.jpc.student.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "estudiantes")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String carrera;

    @Column(nullable = false)
    private Boolean estado;

    @Column(name = "ciclo_actual", nullable = false)
    private Integer cicloActual;

    @Column(unique = true, nullable = false)
    private String documento;
}