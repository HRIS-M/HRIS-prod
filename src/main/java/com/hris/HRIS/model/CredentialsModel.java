package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;

@Getter
@Setter
@ToString

@Document(collection = "Credentials")
public class CredentialsModel {
    @Id
    private String id;
    private String email;
    @Encrypted
    private String password;
    private String level;
}
