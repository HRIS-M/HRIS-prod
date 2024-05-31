package com.hris.HRIS.repository;

import com.hris.HRIS.model.PayrollReportModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PayrollReportRepository extends MongoRepository<PayrollReportModel, String> {
    List<PayrollReportModel> findAllByEmail(String email);
}
