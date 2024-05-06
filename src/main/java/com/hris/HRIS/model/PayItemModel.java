package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "payitem")
public class PayItemModel {
    @Id
    private String id;
    private String organizationId;
    private String itemName;
    private String description;
    private String itemType;
    private String paymentType;
    private String status;
}
