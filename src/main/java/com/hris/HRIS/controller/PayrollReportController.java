package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.EmployeeModel;
import com.hris.HRIS.model.PayrollReportModel;
import com.hris.HRIS.repository.EmployeeRepository;
import com.hris.HRIS.repository.OrganizationRepository;
import com.hris.HRIS.repository.PayrollReportRepository;
import com.hris.HRIS.service.PayrollReportsGenerationService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payrollreport")
public class PayrollReportController {
    @Autowired
    PayrollReportRepository payrollReportRepository;

    @Autowired
    PayrollReportsGenerationService payrollReportsGenerationService;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    EmployeeRepository employeeRepository;

//    @Scheduled(cron = "22 23 09 30 * ?")
//    public void autoGeneratePayrollReports(){
//        System.out.println("Process: Auto Generate Payroll Reports - Running...");
//
//        String payPeriod = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM-yyyy"));
//
//        for(OrganizationModel organizationModel : organizationRepository.findAll()){
//            for(EmployeeModel employeeModel : employeeRepository.findAllByOrganizationId(organizationModel.getId())){
//                if(employeeModel.getStatus().equals("ACTIVE")) {
//                    payrollReportsGenerationService.generatePayrollReport("paysheet", payPeriod, employeeModel.getEmail(), employeeModel.getOrganizationId());
//                }
//            }
//
//            payrollReportsGenerationService.generateSummaryReport("Summary Report", payPeriod, organizationModel.getId());
//        }
//    }

    @PostMapping("/generate/organizationId/{organizationId}/isPreCalculation/{isPreCalculation}")
    public ResponseEntity<ApiResponse> generateAllPayrollReportsByOrganizationId(@PathVariable String organizationId, @PathVariable boolean isPreCalculation) {

        System.out.println("Process: Generate Payroll Reports - Running...");

        String payPeriod = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM-yyyy"));

        for (EmployeeModel employeeModel : organizationRepository.findById(organizationId).get().getEmployees()) {
            payrollReportsGenerationService.generatePayrollReport("paysheet", payPeriod, employeeModel.getEmail(), employeeModel.getOrganizationId(), isPreCalculation);
        }

        payrollReportsGenerationService.generateSummaryReport("Summary Report", payPeriod, organizationId, isPreCalculation);


        ApiResponse apiResponse = new ApiResponse("Reports generated successfuly.");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/{organizationId}/{reportType}/{payPeriod}/generate/{email}")
    public ResponseEntity<ApiResponse> generatePayrollReport(@PathVariable String organizationId, @PathVariable String reportType, @PathVariable String payPeriod, @PathVariable String email) {

        payrollReportsGenerationService.generatePayrollReport(reportType, payPeriod, email, organizationId, false);

        ApiResponse apiResponse = new ApiResponse("Report generated successfuly.");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all/email/{email}")
    public List<PayrollReportModel> getAllPayrollReportsByEmail(@PathVariable String email){

        List<PayrollReportModel> payrollReportsList = payrollReportRepository.findAllByEmail(email);

        Collections.reverse(payrollReportsList);

        return payrollReportsList;
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<PayrollReportModel> getPayrollReportsById(@PathVariable String id){

        Optional<PayrollReportModel> payrollReportModalOptional = payrollReportRepository.findById(id);

        return payrollReportModalOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{reportType}/latest/email/{email}")
    public PayrollReportModel getLatestPayrollReportByEmail(@PathVariable String email){

        List<PayrollReportModel> payrollReportsList = payrollReportRepository.findAllByEmail(email);

        PayrollReportModel payrollReportModal = payrollReportsList.get(payrollReportsList.size() - 1);

        return payrollReportModal;
    }

    @PutMapping("/id/{id}/set-status")
    public ResponseEntity<ApiResponse> changeReportStatus(@PathVariable String id, @RequestBody String status){
        Optional<PayrollReportModel> payrollReportModelOptional = payrollReportRepository.findById(id);

        if(payrollReportModelOptional.isPresent()){
            PayrollReportModel existingPayrollReportModel = payrollReportModelOptional.get();
            existingPayrollReportModel.setStatus(status);

            payrollReportRepository.save(existingPayrollReportModel);
        }

        ApiResponse apiResponse = new ApiResponse("Payroll report status updated successfully.");
        return ResponseEntity.ok(apiResponse);
    }
}
