package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.OnboardingPlanModel;
import com.hris.HRIS.repository.OnboardingPlanRepository;
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

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveOnboardingPlan(@RequestBody OnboardingPlanModel onboardingPlanModel) {
        onboardingPlanRepository.save(onboardingPlanModel);

        ApiResponse apiResponse = new ApiResponse("Onboarding plan saved successfully.");
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
            existingOnboardingPlan.setDescription(onboardingPlanModel.getDescription());
            existingOnboardingPlan.setTaskDate(onboardingPlanModel.getTaskDate());

            onboardingPlanRepository.save(existingOnboardingPlan);

            ApiResponse apiResponse = new ApiResponse("Onboarding plan updated successfully.");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }
}
