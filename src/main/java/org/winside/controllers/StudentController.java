package org.winside.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.winside.entities.Student;
import org.winside.services.StudentService;

@RestController
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "api/v1/student")
    public ResponseEntity<Student> addStudent(@RequestParam String name, @RequestParam int age) {
        return studentService.addStudent(name, age);
    }
}
