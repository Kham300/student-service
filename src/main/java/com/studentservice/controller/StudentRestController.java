package com.studentservice.controller;

import com.studentservice.data.Student;
import com.studentservice.repository.StudentRepository;
import com.studentservice.util.ValidationUtil;
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
import static com.studentservice.util.ValidationUtil.checkNotFoundWithId;

@Slf4j
@RestController
@RequestMapping(value = StudentRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentRestController {
    static final String REST_URL = "/profile";

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping(value = "/{id}")
    public Student getById(@PathVariable int id) {
        log.info("Find student by {}", id);
        return studentRepository.getWithCoursesAndMarks(id);
    }

    @GetMapping(value = "/all")
    public List<Student> findAll() {
        log.info("Find all students");
        return studentRepository.findAll();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/id")
    public void delete(@RequestParam Integer id) {
        log.info("Delete student by {}", id);
        checkNotFoundWithId(studentRepository.delete(id) != 0, id.intValue());
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update existed student")
    public ResponseEntity<?> update(@RequestBody Student student, @PathVariable("id") Integer id) {
        log.info("Update student {}", student);
        assureIdConsistent(student, id);
        return studentRepository.findById(id)
                .map(ignore -> checkNotFoundWithId(studentRepository.save(student), id))
                .map(ignore -> ResponseEntity.ok("Student updated"))
                .orElseGet(() -> ResponseEntity.badRequest().body("Student not found"));
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Register new student")
    public ResponseEntity<Student> register(@Valid @RequestBody Student student) {
        log.info("Register new student {}", student);
        ValidationUtil.checkNew(student);
        Student created = studentRepository.save(student);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/profile")
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
