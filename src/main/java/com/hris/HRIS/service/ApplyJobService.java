package com.hris.HRIS.service;

import com.hris.HRIS.model.ApplyJobModel;
import com.hris.HRIS.repository.ApplyJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplyJobService {

    @Autowired
    ApplyJobRepository applyJobRepository;

    public List<ApplyJobModel> getAllDetails(){

        return applyJobRepository.findAll();

    }

    public ApplyJobModel findById(String id) {

        Optional<ApplyJobModel> resultOptional = applyJobRepository.findById(id);
        return resultOptional.orElse(null); // Return the entity if found, or null if not found

    }

}
