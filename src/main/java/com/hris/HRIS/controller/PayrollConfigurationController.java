package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.PayItemModel;
import com.hris.HRIS.model.PayrollConfigurationModel;
import com.hris.HRIS.repository.PayrollConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/payroll-configuration")
public class PayrollConfigurationController {

    @Autowired
    PayrollConfigurationRepository payrollConfigurationRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> savePayrollConfiguration(@RequestBody PayrollConfigurationModel payrollConfigurationModel) {
        Optional<PayrollConfigurationModel> optionalPayrollConfigurationModel = payrollConfigurationRepository.findByOrganizationId(payrollConfigurationModel.getOrganizationId());

        if (optionalPayrollConfigurationModel.isPresent()) {
            PayrollConfigurationModel existingPayrollConfiguration = optionalPayrollConfigurationModel.get();

            existingPayrollConfiguration.setPayrollFrequency(payrollConfigurationModel.getPayrollFrequency());
            existingPayrollConfiguration.setPayrollPeriodStartDate(payrollConfigurationModel.getPayrollPeriodStartDate());
            existingPayrollConfiguration.setPayrollPeriodInDays(payrollConfigurationModel.getPayrollPeriodInDays());
            existingPayrollConfiguration.setDaysToRunPayroll(payrollConfigurationModel.getDaysToRunPayroll());
            existingPayrollConfiguration.setDaysToPayday(payrollConfigurationModel.getDaysToPayday());

            payrollConfigurationRepository.save(existingPayrollConfiguration);
        }else{
            payrollConfigurationModel.setId(null);
            payrollConfigurationRepository.save(payrollConfigurationModel);
        }

        ApiResponse apiResponse = new ApiResponse("Payroll configuration saved successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/organizationId/{organizationId}")
    public Optional<PayrollConfigurationModel> getPayrollConfigurationByOrganizationId(@PathVariable String organizationId){

        return payrollConfigurationRepository.findByOrganizationId(organizationId);

    }
}
