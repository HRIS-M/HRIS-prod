package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "department")
public class DepartmentModel {
    @Id
    private String id;
    private String name;
    private String description;
    private String organizationId;
}
