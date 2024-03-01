package com.example.FinalStudentProject.service;

import com.example.FinalStudentProject.Entity.Course;
import com.example.FinalStudentProject.Entity.Role;
import com.example.FinalStudentProject.Entity.User;
import com.example.FinalStudentProject.Entity.UserCourseGrade;
import com.example.FinalStudentProject.repo.CourseRepository;
import com.example.FinalStudentProject.repo.RoleRepository;
import com.example.FinalStudentProject.repo.UserCourseGradeRepository;
import com.example.FinalStudentProject.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private UserCourseGradeRepository userCourseGradeRepository;

    // Other service methods...

    public UserCourseGrade findByUserIdAndCourseId(Long userId, Long courseId) {
        List<UserCourseGrade> userCourseGrades = userCourseGradeRepository.findByUserIdAndCourseId(userId, courseId);
        if (!userCourseGrades.isEmpty()) {
            return userCourseGrades.get(0); // Assuming there's only one grade per user and course
        }
        return null; // Or handle the case where no grade is found
    }


    // @Autowired
    //  PasswordEncoder passwordEncoder;

    public void registerDefaultUser(User user) {
        Role roleUser = roleRepo.findByName("User");
        user.addRole(roleUser);

        userRepo.save(user);
    }

    public void saveUserWithDefaultRole(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Role roleUser = roleRepo.findByName("Student");
        user.addRole(roleUser);
        userRepo.save(user);

    }

    public List<User> listAll(){
        return userRepo.findAll();
    }
    public List<Course> listAllCourses(){
        return courseRepo.findAll();
    }

    public Course get(Long id) {
        return courseRepo.findById(id).get();
    }

    public List<Role> listRoles() {
        return roleRepo.findAll();
    }
    public void save(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
    }
    public void saveCourse(Course course) {
        courseRepo.save(course);
    }


}


