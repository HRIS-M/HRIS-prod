package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.DepartmentModel;
import com.hris.HRIS.repository.DepartmentRepository;
import com.hris.HRIS.service.SystemAutomateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    SystemAutomateService systemAutomateService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveDepartment(@RequestBody DepartmentModel departmentModel) {
        departmentRepository.save(departmentModel);

        systemAutomateService.updateOrganizationDepartments(departmentModel);

        systemAutomateService.createDefaultChannels(departmentModel);

        ApiResponse apiResponse = new ApiResponse("Department saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<DepartmentModel> getAllDepartment() {
        return departmentRepository.findAll();
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<DepartmentModel> getDepartmentById(@PathVariable String id) {
        Optional<DepartmentModel> departmentModelOptional = departmentRepository.findById(id);

        return departmentModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/org/{id}")
    public ResponseEntity<List<DepartmentModel>> getDepartmentByOrganizationId(@PathVariable String id) {
        Optional<List<DepartmentModel>> departmentModelOptional = departmentRepository.findAllByOrganizationId(id);

        return departmentModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateDepartment(@PathVariable String id, @RequestBody DepartmentModel departmentModel){
        systemAutomateService.updateDepartmentAndUpdateOrganization(id, departmentModel);

        ApiResponse apiResponse = new ApiResponse("Department updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable String id){
        systemAutomateService.deleteDepartmentAndUpdateOrganization(id);

        systemAutomateService.deleteChannelsWhenDeleteDepartment(id);

        ApiResponse apiResponse = new ApiResponse("Department deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
