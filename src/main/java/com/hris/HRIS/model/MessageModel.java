package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "message")
public class MessageModel {
    @Id
    private String id;
    private String userId;
    private String channelId;
    private String chatId;
    private String content;
    private String status; //sent, received
    private String timestamp;
}
