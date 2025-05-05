package com.example.jpc.enrollment.client;


import com.example.jpc.enrollment.dto.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "jpc-student-service")
//public interface StudentClient {
//
//    @GetMapping("/api/students/{id}")
//    StudentDTO getStudentById(@PathVariable("id") Long id);
//}



import com.example.jpc.enrollment.dto.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "jpc-student-service", fallback = StudentClientFallback.class)
public interface StudentClient {

    @GetMapping("/api/students/{id}")
    StudentDTO getStudentById(@PathVariable("id") Long id);
}
