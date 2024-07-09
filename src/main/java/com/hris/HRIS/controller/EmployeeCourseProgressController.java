package com.hris.HRIS.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.CourseLearningMaterialModal;
import com.hris.HRIS.model.EmployeeCourseProgressModel;
import com.hris.HRIS.repository.CourseLearningMaterialRepository;
import com.hris.HRIS.repository.EmployeeCourseProgressRepository;
import com.hris.HRIS.service.LMSAutomateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/lms/employee-progress")
public class EmployeeCourseProgressController {

    @Autowired
    EmployeeCourseProgressRepository employeeCourseProgressRepository;

    @Autowired
    LMSAutomateService lmsAutomateService;

    @PostMapping("/add-item-completion")
    public ResponseEntity<ApiResponse> getAllLearningMaterialsByModuleId(@RequestBody String requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        String returnMsg = "";

        try {
            JsonNode requestBodyJson = objectMapper.readTree(requestBody);

            String employeeEmail = requestBodyJson.get("employeeEmail").asText();
            String courseId = requestBodyJson.get("courseId").asText();
            String moduleItemId = requestBodyJson.get("moduleItemId").asText();

            List<EmployeeCourseProgressModel> employeeCourseProgressModels = employeeCourseProgressRepository.findAllByEmployeeEmail(employeeEmail);

            Optional<EmployeeCourseProgressModel> existingEmployeeCourseProgressModel = employeeCourseProgressModels.stream()
                    .filter(model -> courseId.equals(model.getCourseId()))
                    .findFirst();

            if(existingEmployeeCourseProgressModel.isPresent()) {
                List<String> completedCourseModulesItems = existingEmployeeCourseProgressModel.get().getCompletedCourseModulesItems();

                if (completedCourseModulesItems != null) {
                    if (!completedCourseModulesItems.contains(moduleItemId)) { // To prevent duplicate entries
                        completedCourseModulesItems.add(moduleItemId);
                    }
                } else {
                    completedCourseModulesItems = new ArrayList<>();
                    completedCourseModulesItems.add(moduleItemId);
                    existingEmployeeCourseProgressModel.get().setCompletedCourseModulesItems(completedCourseModulesItems);
                }

                employeeCourseProgressRepository.save(existingEmployeeCourseProgressModel.get());
            }else{
                EmployeeCourseProgressModel newEmployeeCourseProgressModel = new EmployeeCourseProgressModel();

                newEmployeeCourseProgressModel.setEmployeeEmail(employeeEmail);
                newEmployeeCourseProgressModel.setCourseId(courseId);

                List<String> completedCourseModulesItems = new ArrayList<>();
                completedCourseModulesItems.add(moduleItemId);
                newEmployeeCourseProgressModel.setCompletedCourseModulesItems(completedCourseModulesItems);

                employeeCourseProgressRepository.save(newEmployeeCourseProgressModel);
            }

            lmsAutomateService.calculateEmployeeCourseCompletionProgress(employeeEmail, courseId);
            returnMsg = "Progress saved successfully.";

        } catch (Exception e){
            returnMsg = "Failed to save course progress.";
        }

        ApiResponse apiResponse = new ApiResponse(returnMsg);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/employee/{employeeEmail}/course/{courseId}/get")
    public ResponseEntity<EmployeeCourseProgressModel> getEmployeeCourseProgress(@PathVariable String employeeEmail, @PathVariable String courseId) {
        List<EmployeeCourseProgressModel> employeeCourseProgressModels = employeeCourseProgressRepository.findAllByEmployeeEmail(employeeEmail);

        Optional<EmployeeCourseProgressModel> existingEmployeeCourseProgressModel = employeeCourseProgressModels.stream()
                .filter(model -> courseId.equals(model.getCourseId()))
                .findFirst();

        if(existingEmployeeCourseProgressModel.isPresent()) {
            return ResponseEntity.ok(existingEmployeeCourseProgressModel.get());
        }else{
            EmployeeCourseProgressModel newEmployeeCourseProgressModel = new EmployeeCourseProgressModel();

            newEmployeeCourseProgressModel.setEmployeeEmail(employeeEmail);
            newEmployeeCourseProgressModel.setCourseId(courseId);

            return ResponseEntity.ok(employeeCourseProgressRepository.save(newEmployeeCourseProgressModel));
        }
    }
}
