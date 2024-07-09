package com.hris.HRIS.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@Document(collection = "course-assignment")
public class CourseAssignmentModel {
    @Id
    private String id;
    private String moduleId;
    private String assignmentName;
    private String assignmentDescription;
    private Integer noOfAttempts; // -1 = unlimited attempts
    private Double maximumGradeAllowed; // 20% etc...
    private String startDate;
    private String endDate;
    private String createdDate;
    private String status; // Available, unpublished, Archived.
}
