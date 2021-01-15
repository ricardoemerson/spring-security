package com.example.springsecurity.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private static final List<Student> STUDENTS = Arrays.asList(
        new Student(1, "Crystal Martins"),
        new Student(2, "Dr. Darren Nogueira"),
        new Student(3, "Derek Albuquerque"),
        new Student(4, "Clay Albuquerque"),
        new Student(5, "Ted Barros"),
        new Student(6, "Hugo Carvalho Neto")
    );

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE', 'ROLE_STUDENT')")
    public Student getStudent(@PathVariable("id") Integer id) {
        return STUDENTS.stream()
            .filter(student -> id.equals(student.getId()))
            .findFirst()
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student " + id + " does not exists!")
            );
    }
}
