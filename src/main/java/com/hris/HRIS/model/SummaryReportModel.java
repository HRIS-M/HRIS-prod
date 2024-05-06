package com.hris.HRIS.model;

import com.hris.HRIS.dto.SummaryReportItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString

@Document(collection = "summary-report")
public class SummaryReportModel {
    @Id
    private String id;
    private String organizationId;
    private String reportDescription;
    private List<SummaryReportItem> employeePayments;
    private Double totalEarnings;
    private Double totalDeductions;
    private Double netPayTotal;
    private String reportType;
    private String payPeriod; // July 2024, etc...
    private String reportGeneratedDate;
    private String status;
}
