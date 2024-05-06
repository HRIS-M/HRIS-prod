package com.hris.HRIS.shared.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobData {
    private String position;
    private String department;
    private String salary;
    private String doj;
}
