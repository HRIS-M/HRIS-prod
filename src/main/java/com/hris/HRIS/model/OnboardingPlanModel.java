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
    private String title;
    @Field("onboarding")
    private List<OnboardingModel> onboarding;
    private String description;
    private String startDate;
    private String taskDate;
}
