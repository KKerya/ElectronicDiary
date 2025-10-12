package com.kirillkabylov.NauJava.model;
import java.util.HashMap;
import java.util.Map;


public class Student extends User {
    private Map<String, Integer> grades = new HashMap<>();

    public Map<String, Integer> getGrades(){
        return grades;
    }
}
