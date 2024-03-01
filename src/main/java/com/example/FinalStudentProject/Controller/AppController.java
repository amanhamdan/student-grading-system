package com.example.FinalStudentProject.Controller;

import com.example.FinalStudentProject.*;
import com.example.FinalStudentProject.Entity.Course;
import com.example.FinalStudentProject.Entity.Role;
import com.example.FinalStudentProject.Entity.User;
import com.example.FinalStudentProject.Entity.UserCourseGrade;
import com.example.FinalStudentProject.service.CourseService;
import com.example.FinalStudentProject.service.UserCourseGradeService;
import com.example.FinalStudentProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class AppController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserCourseGradeService userCourseGradeService;

    @GetMapping("/login-success")
    public String loginSuccess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Admin"))) {
                return "admin_user";
            } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Instructor"))) {
                return "instructor_user";
            } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Student"))) {
                return "student_user";
            }
        }
        // Default redirect if user has no role or unknown role
        return "redirect:/login";
    }

    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }
    @GetMapping("/register")
    public String showSignUpForm(Model model){
        model.addAttribute("user",new User());
        return "signup_form";
    }
    @PostMapping("/process_register")
    public String processRegistration(User user){
        userService.saveUserWithDefaultRole(user);
      //  return "register_success";
        return "redirect:/users";
    }

    @GetMapping("/list_users")
    public String viewUsersList(Model model){
        return "users";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userService.listAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.get(id);
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user_form";
    }

    @GetMapping("/grade/edit/{userId}")
    public String editGrade(@PathVariable("userId") Long userId, Model model) {
        User user = userService.get(userId);
        List<Role> listRoles = userService.listRoles();
        UserCourseGrade grade= userService.listGrades(userId);
        model.addAttribute("grade", grade);
         model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "update_grade";
    }


    @PostMapping("/courses/editGrade")
    public String editGrade(@RequestParam("userId") Long userId,
                            @RequestParam("courseId") Long courseId,
                            @RequestParam("grade") Integer grade) {

        // Retrieve the user's grade for the specified course from the database
        UserCourseGrade userCourseGrade = userCourseGradeService.findByUserIdAndCourseId(userId,courseId);

        // Update the grade field
        userCourseGrade.setGrade(grade);

        // Save the updated userCourseGrade
        userCourseGradeService.saveGrade(userCourseGrade);

        // Redirect to the course details page or any appropriate page
        return "redirect:/courses";
    }





    @GetMapping("/course/students/{id}")
    public String courseStudents(@PathVariable("id") Long courseId, Model model) {
        Course course =courseService.get(courseId);
        List<User>  listStudents = userService.listAllUsersByCourse(3L,courseId);

        //TODO
      //  List<UserCourseGrade> userGrades = userCourseGradeService.getAllGradesForUser(userId);
      //  model.addAttribute("userGrades", userGrades);

       model.addAttribute("course", course);
       model.addAttribute("listStudents", listStudents);

        return "instructor_courses_users.html";
    }

    @PostMapping("/courses/save")
    public String CourseUser(Course course, Model model) {
        List<User>  listStudents = userService.listAllUsersByCourse(3L, Long.valueOf(course.getId()));
        courseService.saveCourse(course);
        model.addAttribute("course", course);
        model.addAttribute("listStudents", listStudents);
        return "instructor_courses_users.html";
    }
    @PostMapping("/users/save")
    public String saveUser(User user) {
        userService.save(user);

        return "redirect:/users";
    }

    @PostMapping("/grade/save")
    public String saveGrade(User user) {
        userService.save(user);


       // userCourseGradeService.saveGrade(UserCourseGrade);

        return "instructor_courses_users";
    }



    @GetMapping("/list_courses")
    public String viewCoursesList(Model model){
        return "courses";
    }

    @GetMapping("/courses")
    public String listCourses(Model model) {
        // Retrieve the currently logged-in user's details
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        // Check if the user is an admin
        boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Admin"));
        boolean isInstructor = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Instructor"));
        // If the user is an admin, retrieve all courses
        if (isAdmin) {
            List<Course> listCourses = courseService.listAllCourses();
            model.addAttribute("listCourses", listCourses);
            System.out.println("here are the courses "+ listCourses);
            return "courses";
        } else if(isInstructor) {
            userService.listAllCourses();
          //  User user = service.get(id);
            List<Course> listCourses = userService.listAllCourses();
            // If the user is not an admin, retrieve only the courses assigned to that user
          //  userDetails.getUser().getCourses();
            System.out.println("here are the courses "+ userService.listAllCourses());
            Set<Course> assignedCourses = userDetails.getUser().getCourses();
            model.addAttribute("listCourses", assignedCourses);
            System.out.println("here are the courses "+ assignedCourses);
            return "instructor_courses";
        }else{
            Set<Course> assignedCourses = userDetails.getUser().getCourses();

            model.addAttribute("listCourses", assignedCourses);
            System.out.println("here are the courses "+ assignedCourses);
            return "student_view_course";
        }

       // return "courses";
    }
}
