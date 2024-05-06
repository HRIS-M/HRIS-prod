package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "activities")
public class EmployeeActivitiesModel {
    @Id
    private String id;
    private String userId;
    private String userName;
    private String postId;
    private String posterName;
    private String postCaption;
    private String action;
    private String timestamp;
}
