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
    private ApplyJobRepository applyJobRepository;

    public List<ApplyJobModel> getAllDetails(){

        return applyJobRepository.findAll();

    }

    public ApplyJobModel findById(String id) {

        Optional<ApplyJobModel> resultOptional = applyJobRepository.findById(id);
        return resultOptional.orElse(null); // Return the entity if found, or null if not found

    }

    public void deleteById(String id) {

        applyJobRepository.deleteById(id);
    }

    public long countCvs() {
        return applyJobRepository.countByCvNotNull();
    }

    public long countFavoriteCvs() {
        long count = applyJobRepository.countByFavoriteTrue();

        return count;
    }

    public long getCountOfHiredApplicants() {
        return applyJobRepository.countByHire(true);
    }

    public List<ApplyJobModel> getPendingRecruitments() {
        return applyJobRepository.findPendingRecruitments();
    }
}
