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

@Document(collection = "organization")
public class OrganizationModel {
    @Id
    private String id;
    private String organizationName;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    @Field("departments")
    private List<DepartmentModel> departments;
    @Field("employees")
    private List<EmployeeModel> employees;
    private String description;
    private String photo;
    private int annualLeave;
    private int sickLeave;
    private int casualLeave;
    private int maternityLeave;
    private int paternityLeave;
    private int noPayLeave;
    public Boolean isLeavesConfigured = false;
}
