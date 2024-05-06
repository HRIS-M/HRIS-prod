package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.CourseLearningMaterialModal;
import com.hris.HRIS.repository.CourseLearningMaterialRepository;
import com.hris.HRIS.service.LearningMaterialsManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/v1/lms/course/module/course-learning-material")
public class CourseLearningMaterialController {

    @Autowired
    private MongoDatabaseFactory mongoDbFactory;
    
    @Autowired
    CourseLearningMaterialRepository courseLearningMaterialRepository;

    @Autowired
    LearningMaterialsManagementService learningMaterialsManagementService;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> saveLearningMaterial(@RequestParam("moduleId") String moduleId, 
                                                            @RequestParam("status") String status,
                                                            @RequestParam("file") MultipartFile file
                                                            ) throws IOException {

        learningMaterialsManagementService.saveLearningMaterial(moduleId, "", status, file);

        ApiResponse apiResponse = new ApiResponse("Course learning material saved successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<CourseLearningMaterialModal> getAllLearningMaterials() {
        return courseLearningMaterialRepository.findAll();
    }

    @GetMapping("/get/all/moduleId/{moduleId}")
    public List<CourseLearningMaterialModal> getAllLearningMaterialsByModuleId(@PathVariable String moduleId) {
        return courseLearningMaterialRepository.findAllByModuleId(moduleId);
    }

    @GetMapping("/get/all/assignmentId/{assignmentId}")
    public List<CourseLearningMaterialModal> getAllLearningMaterialsByAssignmentId(@PathVariable String assignmentId) {
        return courseLearningMaterialRepository.findAllByAssignmentId(assignmentId);
    }
    

    @GetMapping("/content/get/id/{id}")
    public ResponseEntity<byte[]> getLearningMaterialContentById(@PathVariable String id) throws IOException {
        
        Optional<CourseLearningMaterialModal> courseLearningMaterialModalOptional = courseLearningMaterialRepository.findById(id);

        if(courseLearningMaterialModalOptional.isPresent()){

            CourseLearningMaterialModal existingCourseLearningMaterial = courseLearningMaterialModalOptional.get();
            return learningMaterialsManagementService.getLearningMaterial(existingCourseLearningMaterial);

        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/get/id/{id}")
    public ResponseEntity<CourseLearningMaterialModal> getLearningMaterialById(@PathVariable String id) {
        Optional<CourseLearningMaterialModal> courseLearningMaterialModalOptional = courseLearningMaterialRepository.findById(id);

        return courseLearningMaterialModalOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateLearningMaterialById(@PathVariable String id, @RequestBody CourseLearningMaterialModal courseLearningMaterialModal) {
        Optional<CourseLearningMaterialModal> courseLearningMaterialModalOptional = courseLearningMaterialRepository.findById(id);
        
        if(courseLearningMaterialModalOptional.isPresent()){
            CourseLearningMaterialModal existingCourseLearningMaterial = courseLearningMaterialModalOptional.get();

            existingCourseLearningMaterial.setModuleId(courseLearningMaterialModal.getModuleId());
            existingCourseLearningMaterial.setStatus(courseLearningMaterialModal.getStatus());
            
            courseLearningMaterialRepository.save(existingCourseLearningMaterial);

            ApiResponse apiResponse = new ApiResponse("Course learning material updated successfully");
            return ResponseEntity.ok(apiResponse);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteLearningMaterialById(@PathVariable String id){
        Optional<CourseLearningMaterialModal> courseLearningMaterialModalOptional = courseLearningMaterialRepository.findById(id);

        if(courseLearningMaterialModalOptional.isPresent()){

            // Delete the file from GridFS.
            learningMaterialsManagementService.deleteLearningMaterial(courseLearningMaterialModalOptional.get().getContentId());

            // Remove the learning material record.
            courseLearningMaterialRepository.deleteById(id);

            ApiResponse apiResponse = new ApiResponse("Learning material deleted successfully");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }
}