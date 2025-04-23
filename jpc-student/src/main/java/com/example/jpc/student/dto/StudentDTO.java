package com.example.jpc.student.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;

    @NotBlank(message = "Document number is required")
    private String documentNumber;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Career is required")
    private String career;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Current cycle is required")
    private Integer currentCycle;

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
        return "StudentDTO{" +
                "id=" + id +
                ", documentNumber='" + documentNumber + '\'' +
                ", name='" + name + '\'' +
                ", career='" + career + '\'' +
                ", status='" + status + '\'' +
                ", currentCycle=" + currentCycle +
                '}';
    }
}