package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Student;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.ArrayList;
import com.kirillkabylov.NauJava.domain.User;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Student> studentContainer(){
        return new ArrayList<>();
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Student> teacherContainer(){
        return new ArrayList<>();
    }
}
