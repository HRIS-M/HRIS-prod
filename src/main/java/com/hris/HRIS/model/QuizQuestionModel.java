package com.hris.HRIS.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@Document(collection = "quiz-question")
public class QuizQuestionModel {
    @Id
    private String id;
    private String quizId;
    private String question;
    private Object options; // [{optionText:"{string}", isCorrect: {bool}}]
    private String questionType;
    private Boolean isMultipleAnswersAllowed;
    private Double scoreAllowed;
    private String preLinkedQuizQuestionId; // This is helps to arrange the already created questions of a quiz according to a order.
}

/*

Sample imput format:
{
    "quizId" : "65f50b65cc752c3fca3f6e50",
    "question" : "What is a typical method used to assess the effectiveness of an Employee Skills Improvement Program?",
    "options" : [
        {"optionText" : "Conducting weekly team meetings", "isCorrect" : false},
        {"optionText" : "Administering pre- and post-training evaluations", "isCorrect" : true},
        {"optionText" : "Issuing quarterly performance bonuses", "isCorrect" : false},
        {"optionText" : "Implementing mandatory overtime schedules", "isCorrect" : false}
    ],
    "questionType" : "multiple choice",
    "isMultipleAnswersAllowed" : false,
    "scoreAllowed" : 2
}

*/