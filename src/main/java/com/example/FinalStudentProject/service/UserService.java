package com.example.FinalStudentProject.service;

import com.example.FinalStudentProject.Entity.Course;
import com.example.FinalStudentProject.Entity.Role;
import com.example.FinalStudentProject.Entity.User;
import com.example.FinalStudentProject.Entity.UserCourseGrade;
import com.example.FinalStudentProject.repo.UserCourseGradeRepository;
import com.example.FinalStudentProject.repo.UserRepository;
import com.example.FinalStudentProject.repo.CourseRepository;
import com.example.FinalStudentProject.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
    public class UserService {

        @Autowired
        private UserRepository userRepo;

        @Autowired
        private RoleRepository roleRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private UserCourseGradeRepository userCourseGradeRepo;
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

    public List<User> listAllUsersByCourse(Long RoleId,Long courseId){
        return userRepo.findByRoleIdAndCourseId(RoleId,courseId);
    }
        //TODO
    public List<User> listAllStudents(){
        return userRepo.findAll();
    }
    public List<User> findUsersByCourseId(Long courseId){
            return userRepo.findUsersByCourseId(courseId);
    }

    public User get(Long id) {
            return userRepo.findById(id).get();
        }
    public List<Course> listAllCourses(){
        return courseRepo.findAll();
    }

        public List<Role> listRoles() {
            return roleRepo.findAll();
        }

    public UserCourseGrade listGrades(Long userId) {
        return userCourseGradeRepo.findById(userId).get();
    }
        public void save(User user) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepo.save(user);
        }

}

