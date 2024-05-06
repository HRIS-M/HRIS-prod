package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "comment")
public class CommentModel {
    @Id
    private String id;
    private String userId;
    private String multimediaId;
    private String messageId;
    private String comment;
    private String timestamp;
}
