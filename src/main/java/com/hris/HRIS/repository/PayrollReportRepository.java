package com.hris.HRIS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hris.HRIS.model.PayrollReportModel;

public interface PayrollReportRepository extends MongoRepository<PayrollReportModel, String> {
    List<PayrollReportModel> findAllByEmail(String email);
}
