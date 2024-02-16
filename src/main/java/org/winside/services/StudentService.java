package org.winside.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.winside.entities.Student;
import org.winside.exceptions.InvalidStudentAgeException;
import org.winside.repositories.StudentRepository;

import static org.winside.entities.ErrorMessage.AGE_ERROR_MESSAGE;

@Service
public class StudentService {
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    StudentRepository studentRepository;

    public ResponseEntity<Student> addStudent(String name, int age) throws InvalidStudentAgeException {
        if (age < 17 || age > 27 ) {
            throw new InvalidStudentAgeException(AGE_ERROR_MESSAGE.get());
        }
        return new ResponseEntity<>(studentRepository.save(new Student(name, age)), HttpStatus.CREATED);
    }
}
