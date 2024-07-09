package com.hris.HRIS.controller;

import com.hris.HRIS.service.ApplyJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recruiter")
public class RecruiterDashboardController {

    @Autowired
    private ApplyJobService applyJobService;

    //No of CVs
    @GetMapping("/count-Cvs")
    public long countCvs() {

        return applyJobService.countCvs();
    }

    //No of Interviewers - In first stage
    @GetMapping("/count-favorite-Cvs")
    public long countFavoriteCvs() {

        return applyJobService.countFavoriteCvs();
    }

    //No of interviews - in hiring stage
    @GetMapping("/count-hired")
    public long getCountOfHiredApplicants() {
        return applyJobService.getCountOfHiredApplicants();
    }

    //Days taken to complete

}
