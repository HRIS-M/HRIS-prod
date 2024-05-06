package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "bullet_in_board")
public class BulletInBoardModel {
    @Id
    private String id;
    private String organizationId;
    private String departmentId;
    private byte[] titleImage;
    private String title;
    private String messages;
    private String redirectUrl;
    private String action;
    private byte[] backgroundImage;
    private String stringBg;
    private String fontColor;
    private String timestamp;
}
