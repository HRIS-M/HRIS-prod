package com.hris.HRIS.model;

import com.hris.HRIS.dto.PayrollReportItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString

@Document(collection = "payrollreport")
public class PayrollReportModel {
    @Id
    private String id;
    private String organizationId;
    private String email;
    private String reportDescription;
    private String reportType;
    private Integer workedDays;
    private List<PayrollReportItem> payItems;
    private List<PayrollReportItem> deductions;
    private double totalEarnings;
    private double totalDeductions;
    private double netPay;
    private String payPeriod; // July 2024, etc...
    private String reportGeneratedDate;
    private String status;
}
