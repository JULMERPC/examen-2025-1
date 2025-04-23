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






package com.example.jpc.course.util;

import com.example.jpc.course.entity.Course;
import com.example.jpc.course.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseSeeder implements CommandLineRunner {

    private final CourseRepository courseRepository;

    public CourseSeeder(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (courseRepository.count() == 0) {
            // Crear objetos Course usando setters
            Course course1 = new Course();
            course1.setCode("C001");
            course1.setName("Matemática I");
            course1.setCapacity(30);
            course1.setSchedule("Lunes y Miércoles 8-10am");
            course1.setCycle(1);

            Course course2 = new Course();
            course2.setCode("C002");
            course2.setName("Física I");
            course2.setCapacity(25);
            course2.setSchedule("Martes y Jueves 10-12am");
            course2.setCycle(2);

            Course course3 = new Course();
            course3.setCode("C003");
            course3.setName("Programación I");
            course3.setCapacity(40);
            course3.setSchedule("Viernes 8-12am");
            course3.setCycle(1);

            Course course4 = new Course();
            course4.setCode("C004");
            course4.setName("Comunicación");
            course4.setCapacity(35);
            course4.setSchedule("Lunes y Jueves 2-4pm");
            course4.setCycle(3);

            // Guardar en base de datos
            courseRepository.save(course1);
            courseRepository.save(course2);
            courseRepository.save(course3);
            courseRepository.save(course4);

            System.out.println("✅ Cursos insertados correctamente.");
        } else {
            System.out.println("⚠️ Ya existen cursos en la base de datos, no se insertaron nuevos.");
        }
    }
}