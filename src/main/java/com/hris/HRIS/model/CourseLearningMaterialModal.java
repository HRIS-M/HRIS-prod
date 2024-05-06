package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "course-learning-material")
public class CourseLearningMaterialModal {
    @Id
    private String id;
    private String moduleId; // Optional - Only if the learning-material/file is not related to an assignment. 
    private String assignmentId; // Optional - Only if the learning-material/file is related to an assignment.
    private String learningMaterialTitle; //Autofills
    private String contentId; //Autofills
    private String contentType; //Autofills
    private String createdDate; //Autofills
    private String status;
}

/*
 Sample request (save):
 "moduleId" : "ENTER_MODULE_ID_HERE"
 "status"   : "ENTER_STATUS_HERE"
 */