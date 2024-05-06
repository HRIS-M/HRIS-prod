package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.EmployeeModel;
import com.hris.HRIS.model.OnboardingModel;
import com.hris.HRIS.repository.OnboardingRepository;
import com.hris.HRIS.service.LettersGenerationService;
import com.hris.HRIS.service.OnboardingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/onboarding")
public class OnboardingController {
    @Autowired
    OnboardingRepository onboardingRepository;

    @Autowired
    OnboardingPlanService onboardingPlanService;

    @Autowired
    LettersGenerationService lettersGenerationService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveOnboarding(@RequestBody OnboardingModel onboardingModel) {
        OnboardingModel model = onboardingRepository.save(onboardingModel);

        onboardingPlanService.setOnboardinsToPlan(model);

        ApiResponse apiResponse = new ApiResponse("Saved onboarding to employee");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/assign/employee/{id}")
    public ResponseEntity<ApiResponse> assignEmployees(@PathVariable String id, @RequestBody List<EmployeeModel> employeeModels) {
        onboardingPlanService.addEmployeesToOnboarding(id, employeeModels);

        ApiResponse apiResponse = new ApiResponse("Saved employees to onboarding");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<OnboardingModel> getAllOnboarding() {
        return onboardingRepository.findAll();
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<OnboardingModel> getOnboardingById(@PathVariable String id) {
        Optional<OnboardingModel> onboardingModelOptional = onboardingRepository.findById(id);

        return onboardingModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/admin/email/{email}")
    public ResponseEntity<OnboardingModel> getOnboardingByAdminEmail(@PathVariable String email) {
        Optional<OnboardingModel> onboardingModelOptional = onboardingRepository.findByAdminEmail(email);

        return onboardingModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateOnboarding(@PathVariable String id, @RequestBody OnboardingModel onboardingModel) {
        Optional<OnboardingModel> onboardingModelOptional = onboardingRepository.findById(id);

        if (onboardingModelOptional.isPresent()) {
            OnboardingModel existingOnboarding = onboardingModelOptional.get();
            existingOnboarding.setOrganizationId(onboardingModel.getOrganizationId());
            existingOnboarding.setOnBoardingPlanId(onboardingModel.getOnBoardingPlanId());
            existingOnboarding.setAdminEmail(onboardingModel.getAdminEmail());
            existingOnboarding.setDescription(onboardingModel.getDescription());
            existingOnboarding.setStartdate(onboardingModel.getStartdate());
            existingOnboarding.setTaskdate(onboardingModel.getTaskdate());
            existingOnboarding.setStatus(onboardingModel.getStatus());

            onboardingRepository.save(existingOnboarding);

            ApiResponse apiResponse = new ApiResponse("Onboarding updated successfully");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/status/{id}")
    public ResponseEntity<ApiResponse> updateOnboardingStatus(@PathVariable String id, @RequestBody OnboardingModel onboardingModel) {
        Optional<OnboardingModel> onboardingModelOptional = onboardingRepository.findById(id);

        if (onboardingModelOptional.isPresent()) {
            OnboardingModel existingOnboarding = onboardingModelOptional.get();
            existingOnboarding.setStatus(onboardingModel.getStatus());

            onboardingRepository.save(existingOnboarding);

            ApiResponse apiResponse = new ApiResponse("Onboarding status updated successfully");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteOnboarding(@PathVariable String id) {
        onboardingRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Onboarding deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/admin/email/{email}")
    public ResponseEntity<ApiResponse> deleteOnboardingByAdminEmail(@PathVariable String email) {
        onboardingRepository.deleteByAdminEmail(email);

        ApiResponse apiResponse = new ApiResponse("Onboarding deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
