package com.hris.HRIS.repository;

import com.hris.HRIS.model.OnboardingModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OnboardingRepository extends MongoRepository<OnboardingModel, String> {

    Optional <OnboardingModel> findByAdminEmail(String email);

    Optional <OnboardingModel> deleteByAdminEmail(String email);
}
