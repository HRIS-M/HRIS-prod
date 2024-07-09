package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "hiringManager")
public class HiringManagerModel {

    @Id
    private String id;

    private String organizationId;

    private String applicant_name;

    private String applicant_email;

    private String hiring_manager_name;

    private String hiring_manager_email;

    private float technical_score;

    private float general_score;

    private float total_score;

    private float satisfaction_score; //means expected score

    private String date;

    private String meeting_start_time;

    private String meeting_end_time;

    private float cost_per_hire;

    private boolean link_valid;
    
}
