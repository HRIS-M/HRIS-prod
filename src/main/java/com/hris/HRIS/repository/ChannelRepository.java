package com.hris.HRIS.repository;

import com.hris.HRIS.model.ChannelModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends MongoRepository<ChannelModel, String> {
    Optional<List<ChannelModel>> findAllByDepartmentId(String departmentId);
}
