package com.kirillkabylov.NauJava.database;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.ArrayList;
import com.kirillkabylov.NauJava.model.User;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<User> userContainer(){
        return new ArrayList<>();
    }
}
