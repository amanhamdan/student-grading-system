package com.example.FinalStudentProject;

import com.example.FinalStudentProject.Entity.Course;
import com.example.FinalStudentProject.Entity.User;
import com.example.FinalStudentProject.repo.CourseRepository;
import com.example.FinalStudentProject.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CourseRepositoryTests {

    @Autowired
    private CourseRepository repo;
    @Autowired
    UserRepository userRepository;

    @Test
    public void testCreateCourses() {
        Course course1 = new Course("math");
        Course course2 = new Course("design_patterns");
        Course course3 = new Course("Java");
        Course course4 = new Course("python");

        repo.saveAll(List.of(course1, course2, course3,course4));

        List<Course> listCourses = repo.findAll();

        assertThat(listCourses.size()).isEqualTo(4);
    }
    @Test
    public void testFindcourseby() {
        // String email = "muayadhaddad@yahoo.com";
        List<User> users =userRepository.findByRoleIdAndCourseId(3L,1l);
        System.out.println("here are the courses "+ users);
        assertThat(users).isNotNull();
    }

}