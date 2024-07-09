package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.OnboardingPlanModel;
import com.hris.HRIS.repository.OnboardingPlanRepository;
import com.hris.HRIS.service.OnboardingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/onboardingPlan")
public class OnboardingPlanController {
    @Autowired
    OnboardingPlanRepository onboardingPlanRepository;

    @Autowired
    OnboardingPlanService onboardingPlanService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveOnboardingPlan(@RequestBody OnboardingPlanModel onboardingPlanModel) {
        OnboardingPlanModel savedOnboardingPlan = onboardingPlanRepository.save(onboardingPlanModel);

        ApiResponse apiResponse = new ApiResponse(savedOnboardingPlan.getId());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<OnboardingPlanModel> getAllOnboardingPlan() {
        return onboardingPlanRepository.findAll();
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<OnboardingPlanModel> getOnboardingPlanById(@PathVariable String id) {
        Optional<OnboardingPlanModel> onboardingPlanModelOptional = onboardingPlanRepository.findById(id);

        return onboardingPlanModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteOnboardingPlan(@PathVariable String id) {
        onboardingPlanService.deleteAllTasksByPlanId(id);
        onboardingPlanRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Onboarding plan deleted successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<ApiResponse> deleteAllOnboardingPlan() {
        onboardingPlanRepository.deleteAll();

        ApiResponse apiResponse = new ApiResponse("All onboarding plans deleted successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateOnboardingPlan(@PathVariable String id, @RequestBody OnboardingPlanModel onboardingPlanModel) {
        Optional<OnboardingPlanModel> onboardingPlanModelOptional = onboardingPlanRepository.findById(id);

        if (onboardingPlanModelOptional.isPresent()) {
            OnboardingPlanModel existingOnboardingPlan = onboardingPlanModelOptional.get();

            existingOnboardingPlan.setOrganizationId(onboardingPlanModel.getOrganizationId());
            existingOnboardingPlan.setEmpName(onboardingPlanModel.getEmpName());
            existingOnboardingPlan.setEmpId(onboardingPlanModel.getEmpId());
            existingOnboardingPlan.setEmpEmail(onboardingPlanModel.getEmpEmail());
            existingOnboardingPlan.setTitle(onboardingPlanModel.getTitle());
            existingOnboardingPlan.setDepartment(onboardingPlanModel.getDepartment());
            existingOnboardingPlan.setManager(onboardingPlanModel.getManager());
            existingOnboardingPlan.setLocation(onboardingPlanModel.getLocation());
            existingOnboardingPlan.setOnboarding(onboardingPlanModel.getOnboarding());
            existingOnboardingPlan.setDescription(onboardingPlanModel.getDescription());
            existingOnboardingPlan.setStartDate(onboardingPlanModel.getStartDate());
            existingOnboardingPlan.setTaskDate(onboardingPlanModel.getTaskDate());
            existingOnboardingPlan.setTaskTitles(onboardingPlanModel.getTaskTitles());
            existingOnboardingPlan.setStatus(onboardingPlanModel.getStatus());
            existingOnboardingPlan.setTemplate(onboardingPlanModel.getTemplate());

            onboardingPlanRepository.save(existingOnboardingPlan);

            ApiResponse apiResponse = new ApiResponse("Onboarding plan updated successfully.");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/status/id/{id}")
    public ResponseEntity<ApiResponse> updateOnboardingPlanStatus(@PathVariable String id) {
        Optional<OnboardingPlanModel> onboardingPlanModelOptional = onboardingPlanRepository.findById(id);

        if (onboardingPlanModelOptional.isPresent()) {
            OnboardingPlanModel existingOnboardingPlan = onboardingPlanModelOptional.get();
            if (existingOnboardingPlan.getStatus().equals("Open")) {
                existingOnboardingPlan.setStatus("Closed");
            } else {
                existingOnboardingPlan.setStatus("Open");
            }
            onboardingPlanRepository.save(existingOnboardingPlan);

            ApiResponse apiResponse = new ApiResponse("Onboarding plan status updated successfully.");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }
}
