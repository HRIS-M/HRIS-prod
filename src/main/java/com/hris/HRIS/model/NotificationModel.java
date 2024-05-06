package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@ToString

@Document(collection = "notifications")
public class NotificationModel {
    @Id
    private String id;
    private String userId;
    private String notification;
    private String timestamp;
    private String router;
    private Boolean status;
}
