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

@Document(collection = "employee")
public class EmployeeModel {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String organizationId;
    private String departmentId;
    @Field("channels")
    private List<ChannelModel> channels;
    private Object jobData;
    private String gender;
    private String dob;
    private String nic;
    private byte[] photo;
    private String status;
    private Integer level;
    private Boolean activityStatus;
    private String lastSeen;
}
