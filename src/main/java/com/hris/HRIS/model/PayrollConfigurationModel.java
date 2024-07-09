package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString

@Document(collection = "payroll-configuration")
public class PayrollConfigurationModel {
    @Id
    private String id;
    private String organizationId;
    private String payrollFrequency; // Monthly,  Biweekly or Weekly
    private String payrollPeriodStartDate;
    private String payrollPeriodInDays;
    private String daysToRunPayroll;
    private String daysToPayday; // The day where the employees receives the payments + payslip.
}
