package com.hris.HRIS.controller;

import com.hris.HRIS.model.SummaryReportModel;
import com.hris.HRIS.repository.SummaryReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1/summaryreport")
public class SummaryReportController {

    @Autowired
    SummaryReportRepository summaryReportRepository;

    @GetMapping("/get/all/organizationId/{organizationId}")
    public List<SummaryReportModel> getAllSummaryReportsByOrganizationId(@PathVariable String organizationId){

        List<SummaryReportModel> summaryReportsList = summaryReportRepository.findAllByOrganizationId(organizationId);

        Collections.reverse(summaryReportsList);

        return summaryReportsList;
    }

}

