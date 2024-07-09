package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@ToString

@Document(collection = "onboardingPlan")
public class OnboardingPlanModel {
    @Id
    private String id;
    private String organizationId;
    private String empName;
    private String empId;
    private String empEmail;
    private String title;
    private String department;
    private String manager;
    private String location;
    @Field("onboarding")
    private List<OnboardingModel> onboarding;
    private String description;
    private String startDate;
    private String taskDate;
    private List<String> taskTitles;
    private String status;
    private String template;
}
