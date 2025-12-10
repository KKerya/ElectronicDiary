package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.UserRepository;
import com.kirillkabylov.NauJava.domain.Admin;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import com.kirillkabylov.NauJava.domain.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(String login, String fullName, String password) {
        if(userRepository.findByLogin(login).isPresent()){
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }
        UserEntity user = new UserEntity(login, fullName, passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public void createUser(UserEntity user) {
        if(userRepository.findByLogin(user.getLogin()).isPresent()){
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<UserEntity> findByFullName(String fullName) {
        List<UserEntity> users = userRepository.findByFullName(fullName);
        if (users.isEmpty()){
            throw new EntityNotFoundException("Users with fullName - " + fullName + "not found");
        }
        return users;
    }

    @Override
    public UserEntity getByLogin(String login){
        return userRepository.findByLogin(login).orElseThrow( () -> new EntityNotFoundException("User with login - " + login + " not found"));
    }

    @Override
    public void deleteUser(UserEntity user){
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userLogin){
        UserEntity appUser = getByLogin(userLogin);
        return new User(appUser.getLogin(), appUser.getPassword(), mapRoles(appUser));
    }

    public Collection<GrantedAuthority> mapRoles(UserEntity appUser){
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (appUser instanceof Admin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (appUser instanceof Teacher) {
            authorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
        } else if (appUser instanceof Student) {
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return authorities;
    }
}