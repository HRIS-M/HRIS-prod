package com.hris.HRIS.model;

import lombok.*;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.PrivateKey;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "Attendance")
public class AttendanceModel {
    @Id
    private String id;
    private String organizationId;
    private String name;
    private String email;
    private String fingerprintDetails;
    private String recordInTime;
    private String recordOutTime;
    private String workShift;
    private String workRoster;
    private String workPattern;
    private String breakStartTime;
    private String breakEndTime;
    private long lateMinutes;
    private long earlyDepartureMinutes;
    private int noPayDays;
    private double overtimeHours;
    private String shiftStartTime;
    private String shiftEndTime;
    private String earliestInTime;
    private String latestOutTime;
    private String deductingHours;
}
