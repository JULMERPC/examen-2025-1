
package com.example.jpc.course.service;

import com.example.jpc.course.dto.CourseDTO;
import com.example.jpc.course.entity.Course;
import com.example.jpc.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        return convertToDTO(course);
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        if (courseRepository.existsByCode(courseDTO.getCode())) {
            throw new RuntimeException("Course code already exists");
        }
        Course course = convertToEntity(courseDTO);
        return convertToDTO(courseRepository.save(course));
    }

    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        // Check if course code already exists for a different course
        if (!existingCourse.getCode().equals(courseDTO.getCode()) &&
                courseRepository.existsByCode(courseDTO.getCode())) {
            throw new RuntimeException("Course code already exists");
        }

        existingCourse.setCode(courseDTO.getCode());
        existingCourse.setName(courseDTO.getName());
        existingCourse.setCapacity(courseDTO.getCapacity());
        existingCourse.setSchedule(courseDTO.getSchedule());
        existingCourse.setCycle(courseDTO.getCycle());

        return convertToDTO(courseRepository.save(existingCourse));
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setCode(course.getCode());
        dto.setName(course.getName());
        dto.setCapacity(course.getCapacity());
        dto.setSchedule(course.getSchedule());
        dto.setCycle(course.getCycle());
        return dto;
    }

    private Course convertToEntity(CourseDTO dto) {
        Course course = new Course();
        course.setId(dto.getId());
        course.setCode(dto.getCode());
        course.setName(dto.getName());
        course.setCapacity(dto.getCapacity());
        course.setSchedule(dto.getSchedule());
        course.setCycle(dto.getCycle());
        return course;
    }
}
