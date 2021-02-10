package com.studentservice.service;

import com.studentservice.controller.dto.MarkDto;
import com.studentservice.data.Mark;

import java.util.List;

public interface GradeService {
    List<Mark> findAllMarkToStudent(Integer studentId);

    double findAverageMarkToStudent(Integer studentId);

    void delete(Integer id);

    Mark markStudent(MarkDto markDto);
}
