package com.hris.HRIS.controller;

import com.hris.HRIS.model.ApplyJobModel;
import com.hris.HRIS.repository.ApplyJobRepository;
import com.hris.HRIS.service.ApplyJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recruiter")
public class RecruiterController {

    @Autowired
    ApplyJobRepository applyJobRepository;

    @Autowired
    ApplyJobService applyJobService;

    @GetMapping("/details")
    public ModelAndView getAllDetails(){
        List<ApplyJobModel> list = applyJobService.getAllDetails();
        return new ModelAndView("CandidateDetails","candidate_details",list);
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

    //update action
    
}
