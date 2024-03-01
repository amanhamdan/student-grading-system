package com.example.FinalStudentProject.Entity;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String name;

  //  @Column(nullable = false, length = 3)
  //  private int grade;

    @OneToMany(mappedBy = "course")
    private Set<UserCourseGrade> courseGrades = new HashSet<>();

    public Set<UserCourseGrade> getCourseGrades() {
        return courseGrades;
    }

    public void setCourseGrades(Set<UserCourseGrade> courseGrades) {
        this.courseGrades = courseGrades;
    }

    public Course() {

    }

    public Course(String name) {
        this.name = name;
    }

    public Course(Integer id) {
        this.id = id;
    }

    public Course(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
//
//    public Course(Integer id, String name,int grade) {
//        this.id = id;
//        this.name = name;
//        this.grade=grade;
//    }

//    public int getGrade() {
//        return grade;
//    }
//
//    public void setGrade(int grade) {
//        this.grade = grade;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
