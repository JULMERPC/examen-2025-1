package com.example.jpc.enrollment.client;


import com.example.jpc.enrollment.dto.CourseDTO;
import org.springframework.stereotype.Component;

@Component
public class CourseClientFallback implements CourseClient {

    @Override
    public CourseDTO getCourseById(Long id) {
        CourseDTO fallbackCourse = new CourseDTO();
        fallbackCourse.setId(id);
        fallbackCourse.setName("Información del curso temporalmente no disponible");
        fallbackCourse.setCapacity(0);
        fallbackCourse.setCode("UNAVAILABLE");
        fallbackCourse.setSchedule("UNAVAILABLE");
        fallbackCourse.setCycle(0);
        return fallbackCourse;
    }

    @Override
    public CourseDTO decreaseCapacity(Long id) {
        // Retornar una respuesta que indique que no se pudo disminuir la capacidad
        CourseDTO fallbackCourse = new CourseDTO();
        fallbackCourse.setId(id);
        fallbackCourse.setName("No se pudo actualizar la capacidad");
        fallbackCourse.setCapacity(-1); // Indicador de que no se pudo actualizar
        return fallbackCourse;
    }
}