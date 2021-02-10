package com.studentservice.service.impl;

import com.studentservice.controller.dto.MarkDto;
import com.studentservice.data.Course;
import com.studentservice.data.GradeValue;
import com.studentservice.data.Mark;
import com.studentservice.data.Student;
import com.studentservice.repository.CourseRepository;
import com.studentservice.repository.MarkRepository;
import com.studentservice.repository.StudentRepository;
import com.studentservice.util.exception.BadRequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceImplTest {

    @Mock
    private MarkRepository markRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private GradeServiceImpl gradeService = new GradeServiceImpl();

    @Captor
    private ArgumentCaptor<Mark> markCaptor;

    @Before
    public void init() {
        mock(false);
    }

    @Test
    public void findAllMarkToStudent_Success() {
        List<Mark> allMarkToStudent = gradeService.findAllMarkToStudent(1);
        assertThat(allMarkToStudent).isNotNull().hasSize(2)
                .anySatisfy(mark -> assertThat(mark.getValue()).isEqualTo(GradeValue.A))
                .anySatisfy(mark -> assertThat(mark.getValue()).isEqualTo(GradeValue.B));
    }

    @Test
    public void findAllMarkToStudent_NotFound() {
        mock(true);
        List<Mark> allMarkToStudent = gradeService.findAllMarkToStudent(1);
        assertThat(allMarkToStudent).isNotNull().isEmpty();
    }

    @Test
    public void findAverageMarkToStudent() {
        double averageMarkToStudent = gradeService.findAverageMarkToStudent(1);

        assertThat(averageMarkToStudent).isEqualTo(4.5);
    }

    @Test
    public void findAverageMarkToStudent_NotFound() {
        mock(true);
        double averageMarkToStudent = gradeService.findAverageMarkToStudent(1);

        assertThat(averageMarkToStudent).isEqualTo(0);
    }

    @Test
    public void markStudent() {
        Mockito.when(markRepository.save(Mockito.any())).thenReturn(new Mark());

        gradeService.markStudent(new MarkDto(LocalDate.now(), GradeValue.A, 1, 1));

        verify(markRepository, times(1)).save(markCaptor.capture());

        assertThat(markCaptor.getValue()).isNotNull().satisfies(value -> {
            assertThat(value.getValue()).isEqualTo(GradeValue.A);
            assertThat(value.getCourse().getTitle()).isEqualTo("test");
            assertThat(value.getStudent().getFirstName()).isEqualTo("test");
        });
    }

    @Test(expected = BadRequestException.class)
    public void markStudent_NotFound() {
        mock(true);
        gradeService.markStudent(new MarkDto(LocalDate.now(), GradeValue.A, 1, 1));
    }

    private void mock(boolean empty) {
        LocalDate now = LocalDate.now();
        Set<Mark> marks = Set.of(new Mark(now, GradeValue.A, null, null), new Mark(now, GradeValue.B, null, null));
        Mockito.when(studentRepository.findById(Mockito.anyInt()))
                .thenReturn( empty ? null :
                        Optional.of(
                                new Student("test@test",
                                        "test",
                                        "test",
                                        "test",
                                        Collections.emptySet(),
                                        marks))
                );

        Mockito.when(courseRepository.findById(Mockito.anyInt()))
                .thenReturn(
                        Optional.of(
                                new Course("test",
                                        Collections.emptySet()))
                );

        Mockito.when(markRepository.findAllByStudentId(Mockito.anyInt())).thenReturn(empty ? null : new ArrayList<>(marks));
    }
}