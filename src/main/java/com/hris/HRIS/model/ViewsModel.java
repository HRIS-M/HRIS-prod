package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "views")
public class ViewsModel {
    @Id
    private String id;
    private String userId;
    private String messageId;
    private String timestamp;
}
