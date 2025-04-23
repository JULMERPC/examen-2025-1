package com.example.jpc.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MsCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCourseApplication.class, args);
    }

}
