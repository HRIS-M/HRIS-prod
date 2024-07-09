package com.hris.HRIS.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hris.HRIS.controller.*;
import com.hris.HRIS.dto.PayrollReportItem;
import com.hris.HRIS.dto.SummaryReportItem;
import com.hris.HRIS.model.*;
import com.hris.HRIS.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

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

    @Autowired
    PayrollConfigurationRepository payrollConfigurationRepository;
    @Autowired
    private SummaryReportController summaryReportController;

    public void generatePayrollReport(String reportType, String payPeriod, String email, String organizationId, boolean isPreCalculation) {
        List<PayrollReportItem> payitems = new ArrayList<>();
        List<PayrollReportItem> deductions = new ArrayList<>();

        PayrollReportModel payrollReportModel = new PayrollReportModel();

        double amount = 0.0;
        double deductedAmount = 0.0;

        employeePayItemController.addEPFDeductions(email);
        employeePayItemController.addETFDeductions(email);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        Optional<PayrollConfigurationModel> payrollConfigurationOptional = payrollConfigurationRepository.findByOrganizationId(organizationId);
        PayrollConfigurationModel existingPayrollConfiguration;
        if (payrollConfigurationOptional.isPresent()) {
            existingPayrollConfiguration = payrollConfigurationOptional.get();
        } else {
            return;
        }

        LocalDateTime payrollPeriodStartDate = LocalDate.parse(existingPayrollConfiguration.getPayrollPeriodStartDate(), formatter2).atStartOfDay();
        LocalDateTime payrollPeriodEndDate = payrollPeriodStartDate.plusDays(Long.parseLong(existingPayrollConfiguration.getPayrollPeriodInDays()) - 1);

        calculatePaymentsRelatedToAttendanceRecords(email, payrollPeriodStartDate.format(formatter), payrollPeriodEndDate.format(formatter));

        List<EmployeePayItemModel> employeePayItemsList = employeePayItemController.getPayItemsByEmail(email);

        // Calculate earnings for the pay items.
        for (int i = 0; i < employeePayItemsList.size(); i++) {
            PayItemModel payItemModel = payItemController.getPayItemById(employeePayItemsList.get(i).getPayItemId()).getBody();

            PayrollReportItem payitem = new PayrollReportItem();

            payitem.setItemName(payItemModel.getItemName());
            payitem.setDescription(payItemModel.getDescription());

            if (employeePayItemsList.get(i).getType().equals("Percentage")) {
                payitem.setAmount(
                        employeePayItemsList.get(0).getValue() * (employeePayItemsList.get(i).getValue() / 100)
                );
            } else if (employeePayItemsList.get(i).getType().equals("Hourly Rate")) {
//                payitem.setAmount(
//                        attendanceController.getHoursWorkedByEmailAndDateRange(email, YearMonth.now()) * employeePayItemsList.get(i).getValue()
//                );
            } else {
                payitem.setAmount(employeePayItemsList.get(i).getValue());
            }

            if (payItemModel.getItemType().equals("Deletion")) {
                deductedAmount += payitem.getAmount();
                deductions.add(payitem);
            } else {
                amount += payitem.getAmount();
                payitems.add(payitem);
            }

        }

        double taxRate = Double.parseDouble(taxController.getTaxRateForSalary(amount - deductedAmount, organizationId).getBody().getMessage());
        double totalTax = (amount - deductedAmount) * (taxRate / 100);

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

        if (!isPreCalculation){
            payrollReportRepository.save(payrollReportModel);
            employeePayItemController.resetCommonPayItems(email);
        }else{
            clearTemporaryPayrollReports(organizationId, email);
            payrollReportModel.setStatus("Temporary report");
            payrollReportRepository.save(payrollReportModel);
        }
    }

    public void generateSummaryReport(String reportType, String payPeriod, String organizationId, boolean isPreCalculation){

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

        if(!isPreCalculation){
            summaryReportModel.setStatus("Available");
        }else{
            clearTemporarySummaryReports(organizationId);
            summaryReportModel.setStatus("Temporary report");
        }

        summaryReportRepository.save(summaryReportModel);
    }

    public void calculatePaymentsRelatedToAttendanceRecords(String email, String startDate, String endDate){
        List<AttendanceModel> attendanceRecords = attendanceController.getAllAttendanceRecordsByEmailAndDateRange(email, startDate, endDate);

        double totalLateMinutes = 0.0;
        double totalEarlyDepartureMinutes = 0.0;
        double totalNoPayHours = 0.0;
        double totalOvertimeHours = 0.0;

        ObjectMapper objectMapper = new ObjectMapper();

        for(AttendanceModel attendanceModel : attendanceRecords){
            totalLateMinutes += attendanceModel.getLateMinutes();
            totalEarlyDepartureMinutes += attendanceModel.getEarlyDepartureMinutes();
            totalOvertimeHours += attendanceModel.getOvertimeHours();
        }

        employeePayItemController.resetCommonPayItems(email);

        // TODO: Temporary added a fixed value as the total hours allowed.

        ObjectNode lateMinuteRecord = objectMapper.createObjectNode();
        lateMinuteRecord.put("totalHoursAllowed", 240);
        lateMinuteRecord.put("lateMinutes", totalLateMinutes);
        employeePayItemController.addLateMinuteDeductions(email, lateMinuteRecord.toString());

        ObjectNode overtimeHoursRecord = objectMapper.createObjectNode();
        overtimeHoursRecord.put("totalHoursAllowed", 240);
        overtimeHoursRecord.put("lateMinutes", totalOvertimeHours);
        employeePayItemController.addOvertimePayments(email, overtimeHoursRecord.toString());
    }

    public void clearTemporaryPayrollReports(String organizationId, String email){
        for(PayrollReportModel payrollReportModel : payrollReportRepository.findAllByEmail(email)){
            if(payrollReportModel.getStatus().equals("Temporary report")){
                payrollReportRepository.delete(payrollReportModel);
            }
        }
    }

    public void clearTemporarySummaryReports(String organizationId){
        for (SummaryReportModel summaryReportModel : summaryReportController.getAllSummaryReportsByOrganizationId(organizationId)) {
            if(summaryReportModel.getStatus().equals("Temporary report")){
                summaryReportRepository.delete(summaryReportModel);
            }
        }
    }
}