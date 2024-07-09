package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.EmployeeExitModel;
import com.hris.HRIS.repository.EmployeeExitRepository;
import com.hris.HRIS.service.SystemAutomateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employee-exit")
public class EmployeeExitController {
    @Autowired
    EmployeeExitRepository employeeExitRepository;

    @Autowired
    SystemAutomateService systemAutomateService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveExitList(@RequestBody EmployeeExitModel employeeExitModel) {
        employeeExitRepository.save(employeeExitModel);

        ApiResponse apiResponse = new ApiResponse("Employee exit request saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<EmployeeExitModel> getAllExitList() {
        return employeeExitRepository.findAll();
    }

    @GetMapping("/get/email/{email}")
    public ResponseEntity<EmployeeExitModel> getExitListByEmail(@PathVariable String email) {
        Optional<EmployeeExitModel> exitListModelOptional = employeeExitRepository.findByEmail(email);

        return exitListModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/email/{email}")
    public ResponseEntity<ApiResponse> updateExitListByEmail(@PathVariable String email, @RequestBody EmployeeExitModel employeeExitModel) {
        Optional<EmployeeExitModel> exitListModelOptional = employeeExitRepository.findByEmail(email);

        if (exitListModelOptional.isPresent()) {
            EmployeeExitModel existingExitList = exitListModelOptional.get();
            existingExitList.setName(employeeExitModel.getName());
            existingExitList.setEmail(employeeExitModel.getEmail());
            existingExitList.setOrganizationId(employeeExitModel.getOrganizationId());
            existingExitList.setPhone(employeeExitModel.getPhone());
            existingExitList.setTelephone(employeeExitModel.getTelephone());
            existingExitList.setAddress(employeeExitModel.getAddress());
            existingExitList.setJobData(employeeExitModel.getJobData());
            existingExitList.setDoe(employeeExitModel.getDoe());
            existingExitList.setDoj(employeeExitModel.getDoj());
            existingExitList.setStatus(employeeExitModel.getStatus());
            existingExitList.setDateOfRetirement(employeeExitModel.getDateOfRetirement());
            existingExitList.setExitReason(employeeExitModel.getExitReason());
            existingExitList.setDateOfContractEnd(employeeExitModel.getDateOfContractEnd());
            existingExitList.setPhoto(employeeExitModel.getPhoto());
            existingExitList.setApproved(employeeExitModel.isApproved());

            employeeExitRepository.save(existingExitList);

            ApiResponse apiResponse = new ApiResponse("Employee exit request updated successfully");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/approve/email/{email}")
    public ResponseEntity<ApiResponse> approveExitListByEmail(@PathVariable String email) {
        Optional<EmployeeExitModel> exitListModelOptional = employeeExitRepository.findByEmail(email);

        if (exitListModelOptional.isPresent()) {
            EmployeeExitModel existingExitList = exitListModelOptional.get();
            existingExitList.setApproved(true);

            employeeExitRepository.save(existingExitList);

            systemAutomateService.addExitList(existingExitList);

            ApiResponse apiResponse = new ApiResponse("Employee exit request approved successfully");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/email/{email}")
    public ResponseEntity<ApiResponse> deleteExitListByEmail(@PathVariable String email) {
        employeeExitRepository.deleteByEmail(email);

        ApiResponse apiResponse = new ApiResponse("Employee exit request deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
