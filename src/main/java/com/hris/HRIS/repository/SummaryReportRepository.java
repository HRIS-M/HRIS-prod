package com.hris.HRIS.repository;

import com.hris.HRIS.model.SummaryReportModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SummaryReportRepository extends MongoRepository<SummaryReportModel, String> {

    List<SummaryReportModel> findAllByOrganizationId(String organizationId);

}