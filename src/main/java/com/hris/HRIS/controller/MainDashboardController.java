package com.hris.HRIS.controller;

import com.hris.HRIS.model.ApplyJobModel;
import com.hris.HRIS.service.ApplyJobService;
import com.hris.HRIS.service.HiringManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main-dashboard")
public class MainDashboardController {

    @Autowired
    private HiringManagerService hiringManagerService;

    @Autowired
    private ApplyJobService applyJobService;

    //calculate average cost per hire
    @GetMapping("/averageCostPerHire")
    public double getAverageCostPerHire() {
        return hiringManagerService.calculateAverageCostPerHire();
    }

    //calculate average time to recruit
    @GetMapping("/averageTimeToRecruit")
    public double getAverageTimeToRecruit() {
        return hiringManagerService.calculateAverageTimeToRecruit();
    }

    //calculate average satisfaction score
    @GetMapping("/averageSatisfactionScore")
    public double getAverageSatisfactionScore() {
        return hiringManagerService.calculateAverageSatisfactionScore();
    }

    //pending recruitments
    @GetMapping("/pending")
    public List<ApplyJobModel> getPendingRecruitments() {
        return applyJobService.getPendingRecruitments();
    }
}
