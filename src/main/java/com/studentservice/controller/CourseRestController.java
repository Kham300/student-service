package com.studentservice.controller;

import com.studentservice.data.Course;
import com.studentservice.data.Student;
import com.studentservice.repository.CourseRepository;
import com.studentservice.repository.StudentRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;

import static com.studentservice.util.ValidationUtil.assureIdConsistent;
import static com.studentservice.util.ValidationUtil.checkNew;
import static com.studentservice.util.ValidationUtil.checkNotFoundWithId;

@Slf4j
@RestController
@RequestMapping(value = CourseRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseRestController {
    static final String REST_URL = "/course";

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find course by id with students")
    public Course getById(@PathVariable("id") Integer id) {
        log.info("Find course by {}", id);
        return checkNotFoundWithId(courseRepository.getCourseWithStudents(id), id);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all courses with students")
    public List<Course> findAll() {
        log.info("Find all courses");
        return courseRepository.findAll();
    }

    @GetMapping(value = "/all/user/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all courses with students")
    public List<Course> findAllByStudentId(@PathVariable("studentId") Integer studentId) {
        log.info("Find all courses to student by id = {}", studentId);
        return courseRepository.findAllByStudentId(studentId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete course")
    public ResponseEntity<?> delete(@RequestParam Integer id) {
        log.info("Delete course by {}", id);
        checkNotFoundWithId(courseRepository.delete(id) != 0, id.intValue());
        return ResponseEntity.ok("resource deleted");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update course")
    public ResponseEntity<?> update(@RequestBody Course course, @PathVariable("id") Integer id) {
        log.info("Update course {}", course);
        assureIdConsistent(course, id);
        return courseRepository.findById(id)
                .map(ignore -> checkNotFoundWithId(courseRepository.save(course), id))
                .map(ignore -> ResponseEntity.ok("Course updated"))
                .orElseGet(() -> ResponseEntity.badRequest().body("Course not found"));
    }

    @PutMapping(value = "/{courseId}/add/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add student to course")
    public ResponseEntity<String> applyCourse(
            @PathVariable("courseId") Integer courseId, @PathVariable("studentId") Integer studentId) {
        log.info("Student {} applies new course {}", studentId, courseId);

        Course course = courseRepository.getCourseWithStudents(courseId);
        Student student = studentRepository.getWithCoursesAndMarks(studentId);

        checkNotFoundWithId(course, courseId);
        checkNotFoundWithId(student, studentId);

        course.getStudents().add(student);
        checkNotFoundWithId(courseRepository.save(course), courseId);

        return ResponseEntity.ok("Course updated");
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create new course")
    public ResponseEntity<Course> create(@Valid @RequestBody Course course) {
        log.info("Register new course {}", course);
        checkNew(course);
        Course created = courseRepository.save(course);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/course")
                .build().toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
