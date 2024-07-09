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

@Document(collection = "onboarding")
public class OnboardingModel {
    @Id
    private String id;
    private String organizationId;
    private String onBoardingPlanId;
    private String taskTitle;
    private String taskName;
    @Field("employees")
    private List<EmployeeModel> employees;
    private String adminEmail;    // Foreign key to EmployeeModel with level 0
    private String description;
    private String startdate;
    private String taskdate;
    private String closed;
    private String status; //onboarding offboarding hold
    private String monitoredBy;
    private String activityNotes;
    private String statusNotes;
}
