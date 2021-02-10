package com.studentservice.repository;

import com.studentservice.data.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface StudentRepository extends JpaRepository<Student, Integer> {

    //https://stackoverflow.com/questions/31978011/what-is-the-difference-between-fetch-and-load-for-entity-graph-of-jpa
    //https://stackoverflow.com/a/46013654/548473
    @EntityGraph(attributePaths = {"marks", "courses"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM Student u WHERE u.id=?1")
    Student getWithCoursesAndMarks(int id);

    //    https://stackoverflow.com/a/46013654/548473
    @EntityGraph(attributePaths = {"marks", "courses"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Student> findAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM Student r WHERE r.id=:id")
    int delete(int id);
}
