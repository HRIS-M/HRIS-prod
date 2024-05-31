package com.hris.HRIS.repository;

import com.hris.HRIS.model.OnboardingPlanModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OnboardingPlanRepository extends MongoRepository<OnboardingPlanModel, String> {
}
