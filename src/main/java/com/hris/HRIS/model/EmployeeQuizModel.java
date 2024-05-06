package com.hris.HRIS.model;

import com.hris.HRIS.dto.QuizAnswer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString

@Document(collection = "employee-quiz")
public class EmployeeQuizModel {
    @Id
    private String id;
    private String employeeEmail;
    private String quizId;
    private List<QuizAnswer> answers; // [{questionId:"{questionId}", answer: {string}}]
    private String attemptDateTime;
    private String submittedDateTime;
    private Double score;
    private String status; // Inprogress, Submitted, Evaluated
}


/*
Submit quiz (sample format):
{
    "quizId" : "65f50b65cc752c3fca3f6e50",
    "employeeEmail" : "test2@gmail.com",
    "answers" : [{"questionId" : "65f510259ec43d367b5fa4cd", "answersGiven":["Fostering career development"]},
                {"questionId" : "65f510829ec43d367b5fa4ce", "answersGiven":["Administering pre- and post-training evaluations"]}]
}
 */