package com.hris.HRIS.repository;

import com.hris.HRIS.model.AttendanceModel;
import com.hris.HRIS.model.LeaveModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LeaveRepository extends MongoRepository<LeaveModel,String> {
    Optional<LeaveModel> findById(String id);

    void deleteById(String id);

}
