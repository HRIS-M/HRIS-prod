package com.hris.HRIS.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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


}
