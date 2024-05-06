package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "tax")
public class TaxModel {
    @Id
    private String id;
    private String organizationId;
    private double min;
    private double max;
    private double rate;
}
