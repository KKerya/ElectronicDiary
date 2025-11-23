package com.kirillkabylov.NauJava;

import com.kirillkabylov.NauJava.Exceptions.UserNotFoundException;
import com.kirillkabylov.NauJava.database.UserRepository;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import com.kirillkabylov.NauJava.domain.UserEntity;
import com.kirillkabylov.NauJava.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserSuccess(){
        when(userRepository.findByLogin("test")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123")).thenReturn("encode123");
        userService.createUser("test", "Test Test", "123");

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void createUserShouldThrowExceptionIfLoginExists() {
        when(userRepository.findByLogin("test"))
                .thenReturn(Optional.of(new UserEntity()));

        assertThrows(RuntimeException.class,
                () -> userService.createUser("test", "User", "123"));
    }

    @Test
    void createUserEntitySuccess() {
        UserEntity user = new UserEntity("test", "Test Test", "123");

        when(userRepository.findByLogin("test")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123")).thenReturn("encoded123");

        userService.createUser(user);

        verify(userRepository).save(user);
        assertEquals("encoded123", user.getPassword());
    }


    @Test
    void findByLoginSuccess() {
        UserEntity user = new UserEntity("test", "User", "123");
        when(userRepository.findByLogin("test")).thenReturn(Optional.of(user));

        UserEntity result = userService.findByLogin("test");

        assertEquals("test", result.getLogin());
    }

    @Test
    void findByLoginNotFound(){
        when(userRepository.findByLogin("unknown")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findByLogin("unknown"));
    }

    @Test
    void loadUserByUsernameSuccess(){
        UserEntity userEntity = new UserEntity("test", "Test Test", "123");
        when(userRepository.findByLogin("test")).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userService.loadUserByUsername("test");
        assertEquals("test", userDetails.getUsername());
        assertEquals("123", userDetails.getPassword());
    }

    @Test
    void loadUserByUsernameNotFound() {
        when(userRepository.findByLogin("test")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> userService.loadUserByUsername("test"));
    }

    @Test
    void mapRolesWhenTeacherHaveTeacherRole(){
        Teacher teacher = new Teacher();
        var roles = userService.mapRoles(teacher);

        assertTrue(roles.stream().anyMatch(role -> role.getAuthority().equals("ROLE_TEACHER")));
    }

    @Test
    void mapRolesStudentDoesNotHaveTeacherAndAdminRole() {
        Student student = new Student();
        var roles = userService.mapRoles(student);

        assertTrue(roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_STUDENT")));
        assertFalse(roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_TEACHER")));
        assertFalse(roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")));
    }
}
