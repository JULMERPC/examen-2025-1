package com.example.jpc.course.controller;

import com.example.jpc.course.dto.CourseDTO;
import com.example.jpc.course.entity.Course;
import com.example.jpc.course.repository.CourseRepository;
import com.example.jpc.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.createCourse(courseDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id,
                                                  @Valid @RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }


    // En CourseController.java del jpc-course-service
    @PutMapping("/{id}/decrease-capacity")
    public ResponseEntity<CourseDTO> decreaseCapacity(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.decreaseCapacity(id));
    }

    // En CourseController.java del jpc-course-service
    @GetMapping("/{id}/available-capacity")
    public ResponseEntity<Integer> getAvailableCapacity(@PathVariable Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        return ResponseEntity.ok(course.getCapacity());
    }



}