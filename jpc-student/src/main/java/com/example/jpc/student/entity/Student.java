package com.example.jpc.student.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
////@NoArgsConstructor
////
//@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Document number is required")
    @Column(unique = true)
    private String documentNumber;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Career is required")
    private String career;

    @NotBlank(message = "Status is required")
    private String status; // active, inactive

    private Integer currentCycle;

//    public Student() {  }
    public Student(Long id, String documentNumber, String name, String career, String status, Integer currentCycle) {
    this.id = id;
    this.documentNumber = documentNumber;
    this.name = name;
    this.career = career;
    this.status = status;
    this.currentCycle = currentCycle;
}

    public Student() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCurrentCycle() {
        return currentCycle;
    }

    public void setCurrentCycle(Integer currentCycle) {
        this.currentCycle = currentCycle;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", documentNumber='" + documentNumber + '\'' +
                ", name='" + name + '\'' +
                ", career='" + career + '\'' +
                ", status='" + status + '\'' +
                ", currentCycle=" + currentCycle +
                '}';
    }
}