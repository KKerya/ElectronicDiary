package com.kirillkabylov.NauJava.controller;

import com.kirillkabylov.NauJava.Exceptions.GradeNotFoundException;
import com.kirillkabylov.NauJava.database.GradeRepository;
import com.kirillkabylov.NauJava.domain.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("custom/grades")
public class GradeController {
    private final GradeRepository gradeRepository;

    @Autowired
    public GradeController(GradeRepository gradeRepository){
        this.gradeRepository = gradeRepository;
    }

    @GetMapping("/student")
    public List<Grade> getGradesByStudentId(@RequestParam Long studentId){
        return gradeRepository.findAllByStudentId(studentId);
    }

    @GetMapping("/filter")
    public Grade getGradeByStudentIdAndSubjectAndValueAndDate(@RequestParam Long studentId,
                                                                    @RequestParam String subject,
                                                                    @RequestParam int value,
                                                                    @RequestParam LocalDateTime date)
    {
       return gradeRepository.findByStudentIdAndSubjectAndValueAndDate(studentId, subject, value, date).orElseThrow(GradeNotFoundException::new);
    }
}
