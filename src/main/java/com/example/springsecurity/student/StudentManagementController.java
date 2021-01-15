package com.example.springsecurity.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
    private static final List<Student> STUDENTS = Arrays.asList(
        new Student(1, "Crystal Martins"),
        new Student(2, "Dr. Darren Nogueira"),
        new Student(3, "Derek Albuquerque"),
        new Student(4, "Clay Albuquerque"),
        new Student(5, "Ted Barros"),
        new Student(6, "Hugo Carvalho Neto")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents() {
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerStudent(@RequestBody Student student) {
        System.out.println("registerStudent");
        System.out.println(student);
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("id") Integer id, @RequestBody Student student) {
        System.out.println("updateStudent");
        System.out.println(String.format("%s %s", id, student));
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("id") Integer id) {
        System.out.println("deleteStudent");
        System.out.println(id);
    }
}
