package com.hris.HRIS.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hris.HRIS.controller.AttendanceController;
import com.hris.HRIS.dto.PayrollReportItem;
import com.hris.HRIS.dto.SummaryReportItem;
import com.hris.HRIS.model.*;
import com.hris.HRIS.repository.EmployeeRepository;
import com.hris.HRIS.repository.OrganizationRepository;
import com.hris.HRIS.repository.SummaryReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.hris.HRIS.controller.EmployeePayItemController;
import com.hris.HRIS.controller.PayItemController;
import com.hris.HRIS.controller.TaxController;
import com.hris.HRIS.repository.PayrollReportRepository;

@Service
public class PayrollReportsGenerationService {

    @Autowired
    EmployeePayItemController employeePayItemController;

    @Autowired
    PayItemController payItemController;

    @Autowired
    PayrollReportRepository payrollReportRepository;

    @Autowired
    TaxController taxController;

    @Autowired
    AttendanceController attendanceController;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    SummaryReportRepository summaryReportRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    public void generatePayrollReport(String reportType, String payPeriod, String email, String organizationId){
        List<EmployeePayItemModel> employeePayItemsList = employeePayItemController.getPayItemsByEmail(email);
        List<PayrollReportItem> payitems = new ArrayList<>();
        List<PayrollReportItem> deductions = new ArrayList<>();

        PayrollReportModel payrollReportModel = new PayrollReportModel();

        double amount = 0.0;
        double deductedAmount = 0.0;

        employeePayItemController.addEPFDeductions(email);
        employeePayItemController.addETFDeductions(email);

        // Calculate earnings for the pay items.
        for(int i = 0; i < employeePayItemsList.size(); i++){
            PayItemModel payItemModel = payItemController.getPayItemById(employeePayItemsList.get(i).getPayItemId()).getBody();

            PayrollReportItem payitem = new PayrollReportItem();

            payitem.setItemName(payItemModel.getItemName());
            payitem.setDescription(payItemModel.getDescription());

            if(employeePayItemsList.get(i).getType().equals("Percentage")) {
                payitem.setAmount(
                        employeePayItemsList.get(0).getValue() * (employeePayItemsList.get(i).getValue() / 100)
                );
            }else if(employeePayItemsList.get(i).getType().equals("Hourly Rate")){
//                payitem.setAmount(
//                        attendanceController.getHoursWorkedByEmailAndDateRange(email, YearMonth.now()) * employeePayItemsList.get(i).getValue()
//                );
            }else{
                payitem.setAmount(employeePayItemsList.get(i).getValue());
            }

            if(payItemModel.getItemType().equals("Deletion")){
                deductedAmount += payitem.getAmount();
                deductions.add(payitem);
            }else{
                amount += payitem.getAmount();
                payitems.add(payitem);
            }

        }

        double taxRate = Double.parseDouble(taxController.getTaxRateForSalary(amount - deductedAmount, organizationId).getBody().getMessage());
        double totalTax = (amount - deductedAmount) * (taxRate/100);

        PayrollReportItem deduction = new PayrollReportItem();
        deduction.setItemName("Tax");
        deduction.setAmount(totalTax);
        deductions.add(deduction);

        deductedAmount += totalTax;

        payrollReportModel.setDeductions(deductions);

        payrollReportModel.setPayItems(payitems);
        payrollReportModel.setTotalEarnings(amount);
        payrollReportModel.setTotalDeductions(deductedAmount);
        payrollReportModel.setNetPay(amount - deductedAmount);
        payrollReportModel.setEmail(email);
        payrollReportModel.setPayPeriod(payPeriod);
        payrollReportModel.setReportType(reportType);
        payrollReportModel.setStatus("Pending approval");

        String generatedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        payrollReportModel.setReportGeneratedDate(generatedDateTime);

        payrollReportRepository.save(payrollReportModel);

        employeePayItemController.resetCommonPayItems(email);
    }

    public void generateSummaryReport(String reportType, String payPeriod, String organizationId){

        double totalEarnings = 0.0;
        double totalDeductions = 0.0;
        double netPayTotal = 0.0;

        SummaryReportModel summaryReportModel = new SummaryReportModel();
        List<SummaryReportItem> summaryReportItemsList = new ArrayList<>();

        for(EmployeeModel employeeModel : organizationRepository.findById(organizationId).get().getEmployees()){

            List<PayrollReportModel> payrollReportsList = payrollReportRepository.findAllByEmail(employeeModel.getEmail());
            Collections.reverse(payrollReportsList);

            for(PayrollReportModel payrollReportModel : payrollReportsList){
                if(payrollReportModel.getPayPeriod().equals(payPeriod) && !payrollReportModel.getStatus().equals("Rejected")){
                    SummaryReportItem summaryReportItem = new SummaryReportItem();

                    summaryReportItem.setEmployeeName(employeeModel.getName());
                    summaryReportItem.setEarnings(payrollReportModel.getTotalEarnings());
                    summaryReportItem.setDeductions(payrollReportModel.getTotalDeductions());
                    summaryReportItem.setNetPay(payrollReportModel.getNetPay());

                    summaryReportItemsList.add(summaryReportItem);

                    totalEarnings += payrollReportModel.getTotalEarnings();
                    totalDeductions += payrollReportModel.getTotalDeductions();
                    netPayTotal += payrollReportModel.getNetPay();

                    break;
                }
            }
        }

        summaryReportModel.setOrganizationId(organizationId);
        summaryReportModel.setReportDescription("Payroll Summary");
        summaryReportModel.setEmployeePayments(summaryReportItemsList);
        summaryReportModel.setTotalEarnings(totalEarnings);
        summaryReportModel.setTotalDeductions(totalDeductions);
        summaryReportModel.setNetPayTotal(netPayTotal);
        summaryReportModel.setReportType(reportType);
        summaryReportModel.setPayPeriod(payPeriod);

        String generatedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        summaryReportModel.setReportGeneratedDate(generatedDateTime);

        summaryReportModel.setStatus("Available");

        summaryReportRepository.save(summaryReportModel);
    }
}