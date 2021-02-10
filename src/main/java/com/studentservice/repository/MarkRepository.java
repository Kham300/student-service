package com.studentservice.repository;

import com.studentservice.data.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MarkRepository extends JpaRepository<Mark, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Mark r WHERE r.id=:id")
    int delete(int id);

    @Query("SELECT d FROM Mark d WHERE d.student.id=:studentId")
    List<Mark> findAllByStudentId(int studentId);
}
