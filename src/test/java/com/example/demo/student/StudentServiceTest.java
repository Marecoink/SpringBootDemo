package com.example.demo.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.Month.NOVEMBER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void CanGetStudents() {
        //when
        underTest.getStudents();
        //then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddNewStudent() {
        //given
        Student student = new Student(
                "Mark",
                "mark@gmail.com",
                LocalDate.of(1992, NOVEMBER, 17)
        );

        //when
        underTest.addNewStudent(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository)
                .save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIstTaken() {
        //given
        Student student = new Student(
                "Mark",
                "mark@gmail.com",
                LocalDate.of(1992, NOVEMBER, 17)
        );

        given(studentRepository.findStudentByEmail(anyString()))
                .willReturn(Optional.of(student));

        //when
        //then
        assertThatThrownBy(() -> underTest.addNewStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email taken");

        verify(studentRepository, never()).save(any());

    }


    @Test
    @Disabled
    void deleteStudent() {
    }

    @Test
    @Disabled
    void updateStudent() {
    }
}