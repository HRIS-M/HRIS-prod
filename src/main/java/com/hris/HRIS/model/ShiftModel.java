package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "employee-shifts")
public class ShiftModel {
    @Id
    private String id;
    private String organizationId;
    private String name;
    private String startTime;
    private String endTime;
    private String description;
}
