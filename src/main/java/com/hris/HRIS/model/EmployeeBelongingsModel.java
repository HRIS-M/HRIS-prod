package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "EmployeeBelongings")
public class EmployeeBelongingsModel {
    @Id
    private String id;
    private String email;
    private String description;
    private Object belongings;
    private String status;
}
