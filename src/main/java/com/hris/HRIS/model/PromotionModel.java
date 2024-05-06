package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "employee-promotion")
public class PromotionModel {
    @Id
    private String id;
    private String userId;
    private String organizationId;
    private String timestamp;
    private String name;
    private String email;
    private String phone;
    private Object jobData;
    private byte [] photo;
    private String reason;
    private String approved;
}
