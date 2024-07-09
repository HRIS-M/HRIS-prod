package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString

@Document(collection = "employee-course-progress")
public class EmployeeCourseProgressModel {
    @Id
    private String id;
    private String employeeEmail;
    private String courseId;
    private List<String> completedCourseModulesItems;
    private Double completedCourseProgress = 0.0;
}
