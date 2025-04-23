//package com.example.jpc.student.util;
//
//
//import com.example.jpc.student.entity.Student;
//
//import com.example.jpc.student.repository.StudentRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class StudentSeeder implements CommandLineRunner {
//
//    private final StudentRepository studentRepository;
//
//    public StudentSeeder(StudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }
//
//    @Override
//    public void run(String... args) {
//
//        if (studentRepository.count() == 0) { // Verificar si la tabla está vacía
//
//            Student s1 = new Student(null, "10000001", "Ana María López", "Ingeniería de Sistemas", "active", 5);
//            Student s2 = new Student(null, "10000002", "Carlos Pérez", "Ingeniería Industrial", "active", 7);
//            Student s3 = new Student(null, "10000003", "Lucía Fernández", "Administración", "inactive", 4);
//            Student s4 = new Student(null, "10000004", "Diego Torres", "Contabilidad", "active", 2);
//            Student s5 = new Student(null, "10000005", "Mariana García", "Marketing", "inactive", 6);
//
//            studentRepository.save(s1);
//            studentRepository.save(s2);
//            studentRepository.save(s3);
//            studentRepository.save(s4);
//            studentRepository.save(s5);
//
//            System.out.println("✅ Datos de estudiantes insertados correctamente.");
//        } else {
//            System.out.println("⚡ Ya existen estudiantes, no se insertaron nuevos datos.");
//        }
//    }
//}






package com.example.jpc.student.util;

import com.example.jpc.student.entity.Student;
import com.example.jpc.student.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StudentSeeder implements CommandLineRunner {

    private final StudentRepository studentRepository;

    public StudentSeeder(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si la tabla de estudiantes está vacía
        if (studentRepository.count() == 0) {
            // Crear objetos de estudiantes
            Student student1 = new Student(null, "12345678", "Juan Pérez", "Ingeniería", "activo", 3);
            Student student2 = new Student(null, "23456789", "Ana Gómez", "Arquitectura", "activo", 2);
            Student student3 = new Student(null, "34567890", "Carlos Díaz", "Medicina", "inactivo", 4);
            Student student4 = new Student(null, "45678901", "Laura Martín", "Derecho", "activo", 1);

            // Guardar en la base de datos
            studentRepository.save(student1);
            studentRepository.save(student2);
            studentRepository.save(student3);
            studentRepository.save(student4);

            System.out.println("✅ Datos de estudiantes insertados correctamente.");
        } else {
            System.out.println("Los estudiantes ya existen, no se insertaron datos.");
        }
    }
}
