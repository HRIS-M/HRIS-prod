package com.hris.HRIS.model;

import com.hris.HRIS.dto.CourseUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString

@Document(collection = "course")
public class CourseModel {
    @Id
    private String id;
    private String organizationId;
    private String courseCode;
    private String courseName;
    private String courseDescription;
    private List<CourseUser> users;
    private Integer enrollmentLimit;
    private String gradingScale; // i.e.: LETTER_GRADES, PERCENTAGE)
    private String startDate;
    private String endDate;
    private String courseCreatedDate;
    private List<String> courseModulesIndexes;
    private String status; // Unpublished, Available, Inprogress, Completed, Archived
}

/*
Sample input:
{
    "courseCode" : "ESIP0265.1",
    "courseName" : "Employee Skills Improvement Program",
    "courseDescription" : "",
    "users" : [],
    "enrollmentLimit" : 20,
    "gradingScale" : "PERCENTAGE",
    "startDate" : "2024-03-16 08:11:35",
    "endDate" : "2024-04-16 08:11:35",
    "status" : "Available"
}
 */