package com.example.FinalStudentProject.repo;

import com.example.FinalStudentProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    @Query(value = "SELECT u.* FROM users u JOIN users_roles ur ON u.id = ur.user_id JOIN roles r ON ur.role_id = r.id WHERE r.id = ?1", nativeQuery = true)
    List<User> findByRoleId(Long roleId);
    @Query(value = "SELECT u.* FROM users u JOIN users_courses ur ON u.id = ur.user_id JOIN courses r ON ur.course_id = r.id WHERE r.id = ?1", nativeQuery = true)
    List<User> findUsersByCourseId(Long courseId);

    @Query("SELECT u FROM User u " +
            "JOIN u.roles ur " +
            "JOIN u.courses uc " +
            "WHERE ur.id = :roleId AND uc.id = :courseId")
    List<User> findByRoleIdAndCourseId(Long roleId, Long courseId);
}
