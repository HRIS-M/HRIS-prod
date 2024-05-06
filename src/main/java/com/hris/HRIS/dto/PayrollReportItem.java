package com.hris.HRIS.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class PayrollReportItem {
    private String itemName;
    private String description;
    private double amount;
}
