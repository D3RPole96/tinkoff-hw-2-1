package edu.example.hw1.repository;

import edu.example.hw1.domain.entity.OperationEntity;
import edu.example.hw1.domain.entity.OperationEntity.OperationType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends MongoRepository<OperationEntity, String> {
    List<OperationEntity> findAllByType(OperationType type);
}
