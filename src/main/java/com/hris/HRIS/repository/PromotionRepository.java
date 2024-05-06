package com.hris.HRIS.repository;

import com.hris.HRIS.model.PromotionModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PromotionRepository extends MongoRepository<PromotionModel, String> {
    Optional<PromotionModel> findByEmail(String email);

    Optional<PromotionModel> deleteByEmail(String email);
}
