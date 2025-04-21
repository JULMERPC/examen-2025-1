package com.example.jpc.student.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private Long id;
    private String nombre;
    private String carrera;
    private Boolean estado;
    private Integer cicloActual;
    private String documento;
}