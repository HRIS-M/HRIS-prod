package com.hris.HRIS.model;

import com.hris.HRIS.enums.LeaveType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "Leave")
public class LeaveModel {
    @Id
    private String id;
    private String organizationId;
    private String empId;
    private String name;
    private String reason;
    private LeaveType leaveType;
    private String leaveStartDate;
    private String leaveEndDate;
    private String approved;
    private String approver;
    private double leaveBalance;
}
