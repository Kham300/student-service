package com.studentservice.controller;

import com.studentservice.controller.dto.MarkDto;
import com.studentservice.data.Mark;
import com.studentservice.service.GradeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = MarkRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MarkRestController {
    static final String REST_URL = "/mark";

    @Autowired
    private GradeService gradeService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all/{studentId}")
    @ApiOperation(value = "Find all marks of student")
    public ResponseEntity<List<Mark>> findAllMarksOfStudent(@PathVariable("studentId") Integer studentId) {
        log.info("Find all to student = {}", studentId);
        return ResponseEntity.ok(gradeService.findAllMarkToStudent(studentId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/average/{studentId}")
    @ApiOperation(value = "Find average marks with students")
    public ResponseEntity<Double> findEverageMarkOfStudent(@PathVariable("studentId") Integer studentId) {
        log.info("Find average grade of student = {}", studentId);
        return ResponseEntity.ok(gradeService.findAverageMarkToStudent(studentId));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete mark")
    public ResponseEntity<?> delete(@RequestParam Integer id) {
        log.info("Delete mark by {}", id);
        gradeService.delete(id);
        return ResponseEntity.ok("resource deleted");
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Grade student")
    public ResponseEntity<Mark> grade(@Validated @RequestBody MarkDto markDto) {
        log.info("New Grade to student {}", markDto);

        Mark created = gradeService.markStudent(markDto);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/mark")
                .build().toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
