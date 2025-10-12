package com.kirillkabylov.NauJava.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User{
    private List<String> subjects = new ArrayList<>();

    public List<String> getSubjects(){
        return subjects;
    }
}
