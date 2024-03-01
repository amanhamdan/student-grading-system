package com.example.FinalStudentProject.repo;

import com.example.FinalStudentProject.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course,Long> {
  @Query("SELECT c FROM Course c WHERE c.name = ?1")
   public Course findByName(String name);

}
