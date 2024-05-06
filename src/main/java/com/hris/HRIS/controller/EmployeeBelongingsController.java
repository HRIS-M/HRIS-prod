package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.EmployeeBelongingsModel;
import com.hris.HRIS.repository.EmployeeBelongingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employee-belongings")
public class EmployeeBelongingsController {
    @Autowired
    EmployeeBelongingsRepository employeeBelongingsRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveEmployeeBelongings(@RequestBody EmployeeBelongingsModel employeeBelongingsModel) {
        employeeBelongingsRepository.save(employeeBelongingsModel);

        ApiResponse apiResponse = new ApiResponse("Employee belongings saved successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<EmployeeBelongingsModel> getAllEmployeeBelongings() {
        return employeeBelongingsRepository.findAll();
    }

    @GetMapping("/get/email/{email}")
    public ResponseEntity<EmployeeBelongingsModel> getEmployeeBelongingsByEmail(@PathVariable String email) {
        Optional<EmployeeBelongingsModel> employeeBelongingsModelOptional = employeeBelongingsRepository.findByEmail(email);

        return employeeBelongingsModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateEmployeeBelongings(@PathVariable String id, @RequestBody EmployeeBelongingsModel employeeBelongingsModel) {
        Optional<EmployeeBelongingsModel> employeeBelongingsModelOptional = employeeBelongingsRepository.findById(id);

        if (employeeBelongingsModelOptional.isPresent()) {
            EmployeeBelongingsModel existingEmployeeBelongings = employeeBelongingsModelOptional.get();
            existingEmployeeBelongings.setEmail(employeeBelongingsModel.getEmail());
            existingEmployeeBelongings.setDescription(employeeBelongingsModel.getDescription());
            existingEmployeeBelongings.setBelongings(employeeBelongingsModel.getBelongings());
            existingEmployeeBelongings.setStatus(employeeBelongingsModel.getStatus());

            employeeBelongingsRepository.save(existingEmployeeBelongings);

            ApiResponse apiResponse = new ApiResponse("Employee belongings updated successfully.");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/email/{email}")
    public ResponseEntity<ApiResponse> updateEmployeeBelongingsByEmail(@PathVariable String email, @RequestBody EmployeeBelongingsModel employeeBelongingsModel) {
        Optional<EmployeeBelongingsModel> employeeBelongingsModelOptional = employeeBelongingsRepository.findByEmail(email);

        if (employeeBelongingsModelOptional.isPresent()) {
            EmployeeBelongingsModel existingEmployeeBelongings = employeeBelongingsModelOptional.get();
            existingEmployeeBelongings.setEmail(employeeBelongingsModel.getEmail());
            existingEmployeeBelongings.setDescription(employeeBelongingsModel.getDescription());
            existingEmployeeBelongings.setBelongings(employeeBelongingsModel.getBelongings());
            existingEmployeeBelongings.setStatus(employeeBelongingsModel.getStatus());

            employeeBelongingsRepository.save(existingEmployeeBelongings);

            ApiResponse apiResponse = new ApiResponse("Employee belongings updated successfully.");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteEmployeeBelongings(@PathVariable String id) {
        employeeBelongingsRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Employee belongings deleted successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/email/{email}")
    public ResponseEntity<ApiResponse> deleteEmployeeBelongingsByEmail(@PathVariable String email) {
        employeeBelongingsRepository.deleteByEmail(email);

        ApiResponse apiResponse = new ApiResponse("Employee belongings deleted successfully.");
        return ResponseEntity.ok(apiResponse);
    }
}
