package com.hris.HRIS.repository;

import com.hris.HRIS.model.BulletInBoardModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BulletInBoardRepository extends MongoRepository<BulletInBoardModel, String> {
}
