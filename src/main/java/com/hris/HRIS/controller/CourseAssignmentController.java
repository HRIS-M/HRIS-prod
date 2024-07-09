package com.hris.HRIS.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.hris.HRIS.model.CourseLearningMaterialModal;
import com.hris.HRIS.service.LearningMaterialsManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.CourseAssignmentModel;
import com.hris.HRIS.repository.CourseAssignmentRepository;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/lms/course/assignment")
public class CourseAssignmentController {
    
    @Autowired
    CourseAssignmentRepository courseAssignmentRepository;

    @Autowired
    LearningMaterialsManagementService learningMaterialsManagementService;

    @Autowired
    CourseLearningMaterialController courseLearningMaterialController;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveAssignment(@RequestBody CourseAssignmentModel courseAssignmentModel) {
        courseAssignmentModel.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        courseAssignmentRepository.save(courseAssignmentModel);

        ApiResponse apiResponse = new ApiResponse("Assignment saved successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/savefile")
    public ResponseEntity<ApiResponse> saveAssignmentFile(@RequestParam("assignmentId") String assignmentId,
                                                          @RequestParam("file") MultipartFile file
                                                            ) throws IOException {

        learningMaterialsManagementService.saveLearningMaterial("", assignmentId, "Published", file);

        ApiResponse apiResponse = new ApiResponse("File saved successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    // TODO: Assignment marking rubric.

    @GetMapping("/get/id/{id}")
    public ResponseEntity<CourseAssignmentModel> getAssignmentById(@PathVariable String id){
        Optional<CourseAssignmentModel> courseAssignmentModelOptional = courseAssignmentRepository.findById(id);

        return courseAssignmentModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/all/moduleId/{moduleId}")
    public List<CourseAssignmentModel> getAllAssignmentsByModuleId(@PathVariable String moduleId){

        return courseAssignmentRepository.findAllByModuleId(moduleId);

    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateAssignment(@PathVariable String id, @RequestBody CourseAssignmentModel courseAssignmentModel){
        Optional<CourseAssignmentModel> courseAssignmentModelOptional = courseAssignmentRepository.findById(id);

        if(courseAssignmentModelOptional.isPresent()){
            CourseAssignmentModel existingCourseAssignment = courseAssignmentModelOptional.get();

            existingCourseAssignment.setModuleId(courseAssignmentModel.getModuleId());
            existingCourseAssignment.setAssignmentName(courseAssignmentModel.getAssignmentName());
            existingCourseAssignment.setAssignmentDescription(courseAssignmentModel.getAssignmentDescription());
            existingCourseAssignment.setNoOfAttempts(courseAssignmentModel.getNoOfAttempts());
            existingCourseAssignment.setMaximumGradeAllowed(courseAssignmentModel.getMaximumGradeAllowed());
            existingCourseAssignment.setStartDate(courseAssignmentModel.getStartDate());
            existingCourseAssignment.setEndDate(courseAssignmentModel.getEndDate());
            existingCourseAssignment.setStatus(courseAssignmentModel.getStatus());

            courseAssignmentRepository.save(existingCourseAssignment);
            
            ApiResponse apiResponse = new ApiResponse("Assignment updated successfully");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteAssignment(@PathVariable String id){
        Optional<CourseAssignmentModel> courseAssignmentModelOptional = courseAssignmentRepository.findById(id);

        if(courseAssignmentModelOptional.isPresent()){
            courseAssignmentRepository.deleteById(id);

            List<CourseLearningMaterialModal> assignmentFilesList = courseLearningMaterialController.getAllLearningMaterialsByAssignmentId(id);

            for (CourseLearningMaterialModal courseLearningMaterialModal : assignmentFilesList) {
                courseLearningMaterialController.deleteLearningMaterialById(courseLearningMaterialModal.getId());
            }

            ApiResponse apiResponse = new ApiResponse("Assignment deleted successfully");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }
}
