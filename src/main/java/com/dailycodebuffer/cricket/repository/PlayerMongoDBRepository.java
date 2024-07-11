package com.dailycodebuffer.cricket.repository;

import com.dailycodebuffer.cricket.entity.PlayerEntity;
import com.dailycodebuffer.cricket.entity.PlayerMongoDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerMongoDBRepository extends MongoRepository<PlayerMongoDBEntity, Integer> {
    List<PlayerEntity> findAllByRole(String role);
}
