package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "document")
public class DocumentModel {
    @Id
    private String id;
    private String organizationId;
    private String adminId;
    private String content;
    private String timestamp;
}
