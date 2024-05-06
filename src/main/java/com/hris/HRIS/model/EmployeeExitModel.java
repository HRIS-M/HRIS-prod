package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "exitRequests")
public class EmployeeExitModel {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Object belongings;
    private Object jobData;
    private String description;
    private String doe; //date of exit
    private String doj; //date of joining
    private String photo;
    private boolean approved;
}
