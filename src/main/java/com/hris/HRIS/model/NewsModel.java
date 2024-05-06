package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "latest-news")
public class NewsModel {
    @Id
    private String id;
    private String organizationId;
    private String description;
    private String redirectUrl;
    private String timestamp;
}
