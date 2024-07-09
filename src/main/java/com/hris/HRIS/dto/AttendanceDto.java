package com.hris.HRIS.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor


public class AttendanceDto {

    private String id;
    private String name;
    private String email;
    private String fingerprintDetails;
    private Date recordInTime;
    private Date recordOutTime;
    private String workShift;
    private String workRoster;
    private String workPattern;
    private Date breakStartTime;
    private Date breakEndTime;
    private String leaveType;
    private Date leaveStartDate;
    private Date leaveEndDate;
    private boolean leaveApproved;
    private double leaveBalance;
    private int lateMinutes;
    private int earlyDepartureMinutes;
    private int noPayDays;
    private double overtimeHours;


}
