package org.winside.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.winside.entities.Student;
import org.winside.exceptions.InvalidStudentAgeException;
import org.winside.repositories.StudentRepository;
import org.winside.services.StudentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.winside.entities.ErrorMessage.AGE_ERROR_MESSAGE;

class ServiceTest {

    StudentService studentService;
    @Mock
    StudentRepository studentRepository;
    int invalidAge;
    String validName;

    @BeforeEach
    public void setUp() {
        studentRepository = mock(StudentRepository.class);
        studentService = new StudentService(studentRepository);
        invalidAge = 45;
        validName = "Omar";
        when(studentRepository.save(any(Student.class))).thenReturn(any(Student.class));
    }

    @Test
    void shouldValidateStudentAge() {

        // WHEN
        InvalidStudentAgeException exceptionThrown = Assertions.assertThrows(InvalidStudentAgeException.class, () -> studentService.addStudent(validName, invalidAge), "Should throw an exception");

        // THEN
        Assertions.assertEquals(400, exceptionThrown.getStatusCode().value());
        Assertions.assertEquals(AGE_ERROR_MESSAGE.get(), exceptionThrown.getReason());
    }
}
