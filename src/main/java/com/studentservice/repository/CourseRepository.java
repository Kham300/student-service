package com.studentservice.repository;

import com.studentservice.data.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @EntityGraph(attributePaths = {"students"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT c FROM Course c LEFT JOIN c.students s WHERE c.id =:id")
    Course getCourseWithStudents(int id);

    @EntityGraph(attributePaths = {"students"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Course> findAll();

    @EntityGraph(attributePaths = {"students"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT c FROM Course c JOIN c.students a WHERE a.id =:studentId")
    List<Course> findAllByStudentId(int studentId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Course r WHERE r.id=:id")
    int delete(int id);
}
