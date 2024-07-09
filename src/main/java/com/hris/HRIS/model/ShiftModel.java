package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "employee-shifts")
public class ShiftModel {
    @Id
    private String id;
    private String organizationId;
    private String longName; // Shift Name
    private String name; //Abbriviation
    private String startTime; // In time
    private String endTime; // Out time
    private String duration; // This will enable when choose flexible nature and after enabling this, in and out time will disappear
    private String earliestInTime; // earliest departures are not valid than this time - in hours
    private String latestOutTime; // latest departures are not valid than this time - in hours
    private String firstHalfDuration; // half day leaves (morning work) - in hours
    private String secondHalfDuration; // half day leaves (evening work) - in hours
    private String shiftNature; // fixed/flexible
    private String offShift; // yes/no or true/false (if this is true whole shift gone as off shift)
    private String deductingHours; // ex: break time, lunch time, etc. - in number (1 hr lunch/ employee need to work 9 hrs to complete 8hr)
    private String minPreOTHours; // minimum pre overtime hours (in number)
    private String minPostOTHours; // minimum post overtime hours (in number)
    private String maxPreOTHours; // maximum pre overtime hours (in number)
    private String maxPostOTHours; // maximum post overtime hours (in number)
    private String description;
}
