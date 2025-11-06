package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.Exceptions.UserNotFoundException;
import com.kirillkabylov.NauJava.database.UserRepository;
import com.kirillkabylov.NauJava.domain.Admin;
import com.kirillkabylov.NauJava.domain.Student;
import com.kirillkabylov.NauJava.domain.Teacher;
import com.kirillkabylov.NauJava.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(String login, String fullName, String password) {
        UserEntity user = new UserEntity(login, fullName, password);
        userRepository.save(user);
    }

    @Override
    public List<UserEntity> findByFullName(String fullName) {
        List<UserEntity> users = userRepository.findByFullName(fullName);
        if (users.isEmpty()){
            throw new UserNotFoundException(fullName);
        }
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        UserEntity appUser = userRepository.findByFullName(username).getFirst();
        return new User(appUser.getFullName(), appUser.getPassword(), mapRoles(appUser));
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