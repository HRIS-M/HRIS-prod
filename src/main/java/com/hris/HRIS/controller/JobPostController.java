package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.JobPostModel;
import com.hris.HRIS.repository.JobPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/jobPost")
public class JobPostController {

    @Autowired
    JobPostRepository jobPostRepository;

    //save details
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveJobPost(@RequestBody JobPostModel jobPostModel){

        jobPostRepository.save(jobPostModel);

        ApiResponse apiResponse = new ApiResponse("Job Posted Successfully");
        return ResponseEntity.ok(apiResponse);
    }

    //get all details
    @GetMapping("/get/all")
    public List<JobPostModel> getAllJobPosts(){

        return jobPostRepository.findAll();
    }

    //update details by id
    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateJobPost(@PathVariable String id, @RequestBody JobPostModel jobPostModel){

        Optional<JobPostModel> jobPostModelOptional = jobPostRepository.findById(id);

        if(jobPostModelOptional.isPresent()){

            JobPostModel existingJobPost = jobPostModelOptional.get();
            existingJobPost.setOrganizationId(jobPostModel.getOrganizationId());
            existingJobPost.setCaption(jobPostModel.getCaption());
            existingJobPost.setAbout_job(jobPostModel.getAbout_job());
            existingJobPost.setTechnical_requirements(jobPostModel.getTechnical_requirements());
            existingJobPost.setEducation_requirements(jobPostModel.getEducation_requirements());
            existingJobPost.setResponsibilities(jobPostModel.getResponsibilities());
            existingJobPost.setExperience_level(jobPostModel.getExperience_level());
            existingJobPost.setOpen_date(jobPostModel.getOpen_date());
            existingJobPost.setEnd_date(jobPostModel.getEnd_date());
            existingJobPost.setContact_email(jobPostModel.getContact_email());
            existingJobPost.setDescription(jobPostModel.getDescription());

            jobPostRepository.save(existingJobPost);
        }

        ApiResponse apiResponse = new ApiResponse("Details Updated Successfully");
        return ResponseEntity.ok(apiResponse);
    }

    //delete details by id
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteJobPost(@PathVariable String id){

        jobPostRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Job Post Deleted Successfully");
        return ResponseEntity.ok(apiResponse);
    }

    //view by id
    @GetMapping("/get/id/{id}")
    public ResponseEntity<ApiResponse> viewJobPost(@PathVariable String id){

        jobPostRepository.findById(id);

        ApiResponse apiResponse = new ApiResponse("View Job Details by Id");
        return ResponseEntity.ok(apiResponse);
    }
}
