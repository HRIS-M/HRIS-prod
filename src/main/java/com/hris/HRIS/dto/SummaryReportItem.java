package com.hris.HRIS.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class SummaryReportItem {

    private String employeeName;
    private Double earnings;
    private Double deductions;
    private Double netPay;

}
