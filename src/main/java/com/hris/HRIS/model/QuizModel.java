package com.hris.HRIS.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@Document(collection = "module-quiz")
public class QuizModel {
    @Id
    private String id;
    private String moduleId;
    private String quizName;
    private String quizDescription;
    // private List<String> questions;
    private Integer noOfAttempts;
    private Double maximumGradeAllowed; // 20% etc...
    private Boolean isRandomized;
    private String startDate;
    private String endDate;
    private String createdDate;
    private String status; // Available, unpublished, Archived.
}

/* 
Sample input:
{
    "moduleId" : "65f509dccc752c3fca3f6e4f",
    "quizName" : "Online test 01",
    "quizDescription" : "Please note that this is a time based test and you have to complete the test within the given time period",
    "noOfAttempts" : 3,
    "maximumGradeAllowed" : 10,
    "isRandomized" : true,
    "startDate" : "2024-03-16 08:11:35",
    "endDate" : "2024-03-28 08:11:35",
    "status" : "Available"
}
*/