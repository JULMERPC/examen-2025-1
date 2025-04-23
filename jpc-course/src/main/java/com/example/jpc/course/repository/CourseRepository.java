package com.example.jpc.course.repository;


import com.example.jpc.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByCode(String code);
    Optional<Course> findByCode(String code);

//    List<Course> id(Integer id);
//
//    List<Course> id(Integer id);
}
