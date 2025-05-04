package com.example.jpc.enrollment.client;


import com.example.jpc.enrollment.dto.CourseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "jpc-course-service")
public interface CourseClient {

    @GetMapping("/api/courses/{id}")
    CourseDTO getCourseById(@PathVariable("id") Long id);

    // En CourseClient.java del jpc-enrollment-service
    @PutMapping("/api/courses/{id}/decrease-capacity")
    CourseDTO decreaseCapacity(@PathVariable("id") Long id);
}