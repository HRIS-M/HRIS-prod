package com.hris.HRIS.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString

public class QuizAnswer {
    private String questionId;
    private List<String> answersGiven;
    private Boolean isCorrect;
    private Double score;
}
