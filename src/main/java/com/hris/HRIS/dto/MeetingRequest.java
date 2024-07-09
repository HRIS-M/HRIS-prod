package com.hris.HRIS.dto;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class MeetingRequest {

    private String id;

    private String meetingDate;

    private String meetingTime;

    private String meetingLink;

    private String userEmail;

    private String managerEmail;

    private boolean notify;
}
