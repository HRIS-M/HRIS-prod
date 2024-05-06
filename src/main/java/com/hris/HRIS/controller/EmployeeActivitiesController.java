package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.EmployeeActivitiesModel;
import com.hris.HRIS.model.LikeModel;
import com.hris.HRIS.repository.EmployeeActivitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/activities")
public class EmployeeActivitiesController {

    @Autowired
    EmployeeActivitiesRepository employeeActivitiesRepository;

    @PostMapping("/toggle")
    public ResponseEntity<ApiResponse> saveLike(@RequestBody EmployeeActivitiesModel employeeActivitiesModel) {
        Optional<EmployeeActivitiesModel> existsActivityModel = employeeActivitiesRepository.findById(employeeActivitiesModel.getId());

        if (existsActivityModel.isPresent()) {
            employeeActivitiesRepository.deleteById(employeeActivitiesModel.getId());
        } else {
            employeeActivitiesRepository.save(employeeActivitiesModel);
        }

        ApiResponse apiResponse = new ApiResponse("Toggled successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveEmployeeActivities(@RequestBody EmployeeActivitiesModel employeeActivitiesModel) {
        employeeActivitiesRepository.save(employeeActivitiesModel);

        ApiResponse apiResponse = new ApiResponse("Employee activities saved successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<EmployeeActivitiesModel> getAllEmployeeActivities() { return employeeActivitiesRepository.findAll(); }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteEmployeeActivityById(@PathVariable String id) {
        employeeActivitiesRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Employee activity deleted successfully.");
        return ResponseEntity.ok(apiResponse);
    }
}
