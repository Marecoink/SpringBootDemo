package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.Month.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckWhenStudentEmailExists() {
        //given
        String email = "mark@gmail.com";
        Student student = new Student(
                "Mark",
                email,
                LocalDate.of(1992, NOVEMBER, 17)
        );
        underTest.save(student);
        //when
        Optional<Student> studentOptional = underTest.findStudentByEmail(email);
        boolean expected = studentOptional.isPresent();
        //then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckWhenStudentEmailDoesNotExists() {
        //given
        String email = "mark@gmail.com";
        //when
        Optional<Student> studentOptional = underTest.findStudentByEmail(email);
        boolean expected = studentOptional.isPresent();
        //then
        assertThat(expected).isFalse();
    }
}