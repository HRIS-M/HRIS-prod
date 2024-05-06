package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@ToString

@Document(collection = "applyJob")
public class ApplyJobModel {

    @Id
    private String id;

    private String name;

    private String contact_number;

    private String email;

    private byte[] cv;
}
