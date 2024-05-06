package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "openUser")
public class OpenUserModel {

    @Id
    private String Id;
    private String name;
    private String email;
    private String phone_number;
}
