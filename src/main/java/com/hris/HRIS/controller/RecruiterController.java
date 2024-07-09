package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.dto.HireRequest;
import com.hris.HRIS.dto.MeetingRequest;
import com.hris.HRIS.model.ApplyJobModel;
import com.hris.HRIS.repository.ApplyJobRepository;
import com.hris.HRIS.service.ApplyJobService;
import com.hris.HRIS.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/recruiter")
public class RecruiterController {

    @Autowired
    ApplyJobRepository applyJobRepository;

    @Autowired
    ApplyJobService applyJobService;

    @Autowired
    EmailService emailService;

    @GetMapping("/details")
    public List<ApplyJobModel> getAllDetails(){
        return applyJobRepository.findAll();
    }

    //download cv
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadCV(@PathVariable String id){

        ApplyJobModel applyJob = applyJobService.findById(id);

        if(applyJob == null){
            return ResponseEntity.notFound().build();
        }

        byte[] cvContent = applyJob.getCv();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(cvContent);
    }

    // Schedule meeting and send email
    @PostMapping("/schedule-meeting")
    public ResponseEntity<ApiResponse> scheduleMeeting(@RequestBody MeetingRequest meetingRequest) {

        Optional<ApplyJobModel> optionalJob = applyJobRepository.findById(meetingRequest.getId());

        if (optionalJob.isPresent()) {
            ApplyJobModel job = optionalJob.get();
            job.setMeeting_date(meetingRequest.getMeetingDate());
            job.setMeeting_time(meetingRequest.getMeetingTime());
            job.setMeeting_link(meetingRequest.getMeetingLink());
            job.setAction(true);  // Update action to true (selected)

            applyJobRepository.save(job);

            // Send email to user and manager
            emailService.sendMeetingDetails(job, meetingRequest.getUserEmail(), meetingRequest.getManagerEmail(), meetingRequest.isNotify());

            ApiResponse apiResponse = new ApiResponse("Meeting scheduled and email sent");
            return ResponseEntity.ok(apiResponse);

        } else {
            ApiResponse apiResponse = new ApiResponse("Job not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    //delete candidates
    @DeleteMapping("/delete-candidate/{id}")
    public ResponseEntity<ApiResponse> deleteCandidate(@PathVariable("id") String id){

        applyJobService.deleteById(id);
        ApiResponse apiResponse = new ApiResponse("Candidate deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    //select candidates
    @GetMapping ("/select-candidate/{id}")
    public ResponseEntity<ApiResponse> selectCandidate(@PathVariable("id") String id){

        String message = "Candidate selected successfully";

        ApiResponse apiResponse = new ApiResponse(message);
        return ResponseEntity.ok(apiResponse);

    }

    // Hire candidates and send email
    @PostMapping("/hire-candidates")
    public ResponseEntity<ApiResponse> hireCandidates(@RequestBody HireRequest hireRequest) {

        List<String> candidateIds = hireRequest.getCandidateIds();
        List<ApplyJobModel> allCandidates = applyJobService.getAllDetails();

        for (ApplyJobModel candidate : allCandidates) {
            if (candidateIds.contains(candidate.getId())) {
                candidate.setHire(true);
                applyJobRepository.save(candidate);
                emailService.sendHireNotification(candidate.getEmail(), candidate.getName());
            } else {
                candidate.setHire(false);
                applyJobRepository.save(candidate);
                emailService.sendRejectionNotification(candidate.getEmail(), candidate.getName());
            }
        }

        ApiResponse apiResponse = new ApiResponse("Candidates updated and emails sent");
        return ResponseEntity.ok(apiResponse);
    }

    // select applicant as favorite
    @PostMapping("/favorite/{id}")
    public ResponseEntity<ApiResponse> favorite(@PathVariable String id) {
        Optional<ApplyJobModel> optionalJob = applyJobRepository.findById(id);

        if (optionalJob.isPresent()) {
            ApplyJobModel job = optionalJob.get();

            job.setFavorite(!job.isFavorite());
            applyJobRepository.save(job);
        }
        ApiResponse apiResponse = new ApiResponse("Candidate added to favorites");
        return ResponseEntity.ok(apiResponse);
    }
}
