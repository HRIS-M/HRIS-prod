package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.CourseModuleModal;
import com.hris.HRIS.repository.CourseModuleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/lms/course-module")
public class CourseModuleController {
    @Autowired
    CourseModuleRepository courseModuleRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveCourseModule(@RequestBody CourseModuleModal courseModuleModal) {
        courseModuleModal.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        courseModuleRepository.save(courseModuleModal);

        ApiResponse apiResponse = new ApiResponse("Course module created successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<CourseModuleModal> getAllCourseModules(){
        return courseModuleRepository.findAll();
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<CourseModuleModal> getCourseModuleById(@PathVariable String id){
        Optional<CourseModuleModal> courseModuleModalOptional = courseModuleRepository.findById(id);

        return courseModuleModalOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/all/courseId/{courseId}")
    public List<CourseModuleModal> getAllCourseModulesByCourseId(@PathVariable String courseId){

        List<CourseModuleModal> CourseModulesList = courseModuleRepository.findAllByCourseId(courseId);

        return CourseModulesList;
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateCourseModule(@PathVariable String id, @RequestBody CourseModuleModal courseModuleModal){
        Optional<CourseModuleModal> courseModuleModalOptional = courseModuleRepository.findById(id);

        if(courseModuleModalOptional.isPresent()){
            CourseModuleModal existingCourseModule = courseModuleModalOptional.get();
            existingCourseModule.setModuleTitle(courseModuleModal.getModuleTitle());
            existingCourseModule.setModuleDescription(courseModuleModal.getModuleDescription());
            existingCourseModule.setEstimatedMinutesToComplete(courseModuleModal.getEstimatedMinutesToComplete());
            existingCourseModule.setPreLinkedModuleId(courseModuleModal.getPreLinkedModuleId());
            existingCourseModule.setStartDate(courseModuleModal.getStartDate());
            existingCourseModule.setEndDate(courseModuleModal.getEndDate());
            existingCourseModule.setStatus(courseModuleModal.getStatus());

            courseModuleRepository.save(existingCourseModule);
        }

        ApiResponse apiResponse = new ApiResponse("Course module updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteCourseModule(@PathVariable String id){
        courseModuleRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Course module deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
