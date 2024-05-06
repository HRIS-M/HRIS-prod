package com.hris.HRIS.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@Document(collection = "course-module")
public class CourseModuleModal {
    @Id
    private String id;
    private String courseId;
    private String moduleTitle;
    private String moduleDescription;
    private Integer estimatedMinutesToComplete;
    private String preLinkedModuleId; // This is helps to arrange the already created modules according to a order.
    private String startDate;
    private String endDate;
    private String createdDate;
    private String status; // Available, unpublished, Archived.
}

/*
Sample input:
{
    "moduleId" : "65f509dccc752c3fca3f6e4f",
    "moduleTitle" : "Week 1",
    "moduleDescription" : "",
    "estimatedMinutesToComplete" : 20,
    "preLinkedModuleId" : "",
    "startDate" : "2024-03-16 08:11:35",
    "endDate" : "2024-03-28 08:11:35",
    "status" : "Available"
}
 */