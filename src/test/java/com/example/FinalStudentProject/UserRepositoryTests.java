package com.example.FinalStudentProject;

import com.example.FinalStudentProject.Entity.Course;
import com.example.FinalStudentProject.Entity.Role;
import com.example.FinalStudentProject.Entity.User;
import com.example.FinalStudentProject.repo.CourseRepository;
import com.example.FinalStudentProject.repo.RoleRepository;
import com.example.FinalStudentProject.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {



    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("aman@yahoo.com");
        user.setPassword("123") ;
        user.setFirstName("aman");
        user.setLastName("hamdan");

        User savedUser = userRepo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());
        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }
    @Test
    public void testAddRoleToNewUser() {
        Role roleAdmin = roleRepo.findByName("Admin");

        User user = new User();
        user.setEmail("mikes.gates@gmail.com");
        user.setPassword("mike2020");
        user.setFirstName("Mike");
        user.setLastName("Gates");
        user.addRole(roleAdmin);

        User savedUser = userRepo.save(user);

        assertThat(savedUser.getRoles().size()).isEqualTo(1);
    }
    @Test
    public void testAddRoleToExistingUser() {
        User user = userRepo.findById(1L).get();
        Role roleUser = roleRepo.findByName("User");

        Role roleCustomer = new Role(3);
        user.addRole(roleUser);
        user.addRole(roleCustomer);

        User savedUser = userRepo.save(user);

        assertThat(savedUser.getRoles().size()).isEqualTo(2);
    }

    @Test
    public void testAddCourseToExistingUser() {
        User user = userRepo.findById(6L).get();
        Course course = courseRepo.findByName("User");

        Course courseCustomer = new Course(2);
        user.addCourse(course);
        user.getRoles();
        user.addCourse(courseCustomer);

        User savedUser = userRepo.save(user);

        assertThat(savedUser.getCourses().size()).isEqualTo(2);
    }

    @Test
    public void testFindUserByEmail() {
        String email = "muayadhaddad@yahoo.com";
        User user = userRepo.findByEmail(email);

        assertThat(user).isNotNull();
    }
    @Test
    public void testFindUserByRole() {
       // String email = "muayadhaddad@yahoo.com";
        List<User> users =userRepo.findByRoleId(2L);
        System.out.println("here are the courses "+ users);
        assertThat(users).isNotNull();
    }
}
