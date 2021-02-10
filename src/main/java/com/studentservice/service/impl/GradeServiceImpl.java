package com.studentservice.service.impl;

import com.studentservice.controller.dto.MarkDto;
import com.studentservice.data.Course;
import com.studentservice.data.GradeValue;
import com.studentservice.data.Mark;
import com.studentservice.data.Student;
import com.studentservice.repository.CourseRepository;
import com.studentservice.repository.MarkRepository;
import com.studentservice.repository.StudentRepository;
import com.studentservice.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.studentservice.util.ValidationUtil.checkNotFoundWithId;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Mark> findAllMarkToStudent(Integer studentId) {
        checkNotFoundWithId(studentRepository.findById(studentId).orElse(null), studentId.intValue());

        return Optional.ofNullable(markRepository.findAllByStudentId(studentId))
                .orElse(Collections.emptyList());
    }

    @Override
    public double findAverageMarkToStudent(Integer studentId) {
        checkNotFoundWithId(studentRepository.findById(studentId).orElse(null), studentId.intValue());

        return Optional.ofNullable(markRepository.findAllByStudentId(studentId))
                .orElse(Collections.emptyList()).stream()
                .map(Mark::getValue)
                .mapToInt(GradeValue::getValue)
                .average().orElse(0);
    }

    @Override
    public void delete(Integer id) {
        checkNotFoundWithId(markRepository.delete(id) != 0, id.intValue());
    }

    @Override
    public Mark markStudent(MarkDto markDto) {
        Mark mark = new Mark();

        Course course = courseRepository.findById(markDto.getCourseId())
                .orElse(null);
        Student student = studentRepository.findById(markDto.getStudentId())
                .orElse(null);

        mark.setCourse(checkNotFoundWithId(course, markDto.getCourseId()));
        mark.setStudent(checkNotFoundWithId(student, markDto.getStudentId()));
        mark.setValue(markDto.getValue());
        mark.setVoteDate(markDto.getMarkDate());

        return markRepository.save(mark);
    }
}
