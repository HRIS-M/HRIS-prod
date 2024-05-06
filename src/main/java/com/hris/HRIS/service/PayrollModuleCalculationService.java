package com.hris.HRIS.service;

import org.springframework.stereotype.Service;

@Service
public class PayrollModuleCalculationService {
    public Double calculateOvertimePayments(double basicSalary, double totalHoursAllowed, double overtimeHoursWorked){
        
        Double overtimePaymentsPerHour = basicSalary/totalHoursAllowed;

        // overtimePayments = overtimeHoursWorked * (overtimePaymentsPerHour + overtimePaymentsPerHour/2);
        return overtimeHoursWorked * overtimePaymentsPerHour;

    }

    public Double calculateLateMinuteDeductions(double basicSalary, double totalHoursAllowed, double lateMinutes){
        
        Double deductionAmountPerHour = basicSalary/totalHoursAllowed;

        return (lateMinutes/60) * deductionAmountPerHour;
    }

    public Double calculateNoPayHoursDeductions(double basicSalary, double totalHoursAllowed, double noPayHours){
        
        return (noPayHours/totalHoursAllowed) * basicSalary;

    }

    public Double calculateEPF(double basicSalary, double EPF_rate){

        return basicSalary * (EPF_rate/100);

    }

    public Double calculateETF(double basicSalary, double ETF_rate){

        return basicSalary * (ETF_rate/100);
        
    }
}
