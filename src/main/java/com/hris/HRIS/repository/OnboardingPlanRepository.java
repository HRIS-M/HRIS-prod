package com.hris.HRIS.repository;

import com.hris.HRIS.model.OnboardingPlanModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OnboardingPlanRepository extends MongoRepository<OnboardingPlanModel, String> {
}
