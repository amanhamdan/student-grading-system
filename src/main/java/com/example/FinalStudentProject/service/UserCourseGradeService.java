package com.example.FinalStudentProject.service;


import com.example.FinalStudentProject.Entity.UserCourseGrade;
import com.example.FinalStudentProject.repo.UserCourseGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserCourseGradeService {

    @Autowired
    private UserCourseGradeRepository userCourseGradeRepository;

    public List<UserCourseGrade> getAllGradesForUser(Long userId) {
        return userCourseGradeRepository.findByUserId(userId);
    }

    public List<UserCourseGrade> getAllGradesForCourse(Long courseId) {
        return userCourseGradeRepository.findByCourseId(courseId);
    }


    public UserCourseGrade get(Long id) {
        return userCourseGradeRepository.findById(id).get();
    }

   // public static UserCourseGrade get(Long id) {return userCourseGradeRepository.findById(id).get();}

    public void saveGrade(UserCourseGrade userCourseGrade) {
        userCourseGradeRepository.save(userCourseGrade);
    }

    public UserCourseGrade findByUserIdAndCourseId(Long userId, Long courseId) {
        List<UserCourseGrade> userCourseGrades = userCourseGradeRepository.findByUserIdAndCourseId(userId, courseId);
        if (!userCourseGrades.isEmpty()) {
            return userCourseGrades.get(0); // Assuming there's only one grade per user and course
        }
        return null; // Or handle the case where no grade is found
    }




}