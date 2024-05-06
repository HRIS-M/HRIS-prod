package com.hris.HRIS.repository;

import com.hris.HRIS.model.PayItemModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PayItemRepository extends MongoRepository<PayItemModel, String> {

    Optional<PayItemModel> findOneByItemName(String itemName);
    List<PayItemModel> findAllByOrganizationId(String organizationId);

}
