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






package com.example.jpc.enrollment.util;

import com.example.jpc.enrollment.entity.Enrollment;
import com.example.jpc.enrollment.repository.EnrollmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentSeeder implements CommandLineRunner {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentSeeder(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (enrollmentRepository.count() == 0) {
            Enrollment enrollment1 = new Enrollment();
            enrollment1.setStudentId(1L);
            enrollment1.setCourseId(101L);
            enrollment1.setCycle(1);

            Enrollment enrollment2 = new Enrollment();
            enrollment2.setStudentId(2L);
            enrollment2.setCourseId(102L);
            enrollment2.setCycle(2);

            Enrollment enrollment3 = new Enrollment();
            enrollment3.setStudentId(3L);
            enrollment3.setCourseId(103L);
            enrollment3.setCycle(3);

            Enrollment enrollment4 = new Enrollment();
            enrollment4.setStudentId(4L);
            enrollment4.setCourseId(104L);
            enrollment4.setCycle(1);

            enrollmentRepository.save(enrollment1);
            enrollmentRepository.save(enrollment2);
            enrollmentRepository.save(enrollment3);
            enrollmentRepository.save(enrollment4);

            System.out.println("✅ Datos de inscripciones insertados correctamente.");
        } else {
            System.out.println("Las inscripciones ya existen, no se insertaron datos.");
        }
    }
}