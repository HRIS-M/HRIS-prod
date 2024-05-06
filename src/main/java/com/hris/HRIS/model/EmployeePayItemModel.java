package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "employee-payitem")
public class EmployeePayItemModel {
    @Id
    private String id;
    private String payItemId;
    private String email;
    private String type;
    private double value;
}
