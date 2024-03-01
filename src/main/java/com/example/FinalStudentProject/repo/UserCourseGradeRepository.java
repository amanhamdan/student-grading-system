package com.example.FinalStudentProject.repo;

import com.example.FinalStudentProject.Entity.UserCourseGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCourseGradeRepository extends JpaRepository<UserCourseGrade, Long> {


    List<UserCourseGrade> findByUserId(Long userId);
    List<UserCourseGrade> findByCourseId(Long courseId);
    List<UserCourseGrade> findByUserIdAndCourseId(Long userId, Long courseId);
}