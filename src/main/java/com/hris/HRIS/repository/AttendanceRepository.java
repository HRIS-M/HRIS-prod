package com.hris.HRIS.repository;

import com.hris.HRIS.model.AttendanceModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends MongoRepository<AttendanceModel, String> {
    Optional<AttendanceModel> findById(String id);
    List<AttendanceModel> findByEmail(String email);
    void deleteById(String id);

}


