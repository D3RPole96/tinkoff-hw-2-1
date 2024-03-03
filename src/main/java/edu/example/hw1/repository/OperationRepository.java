package edu.example.hw1.repository;

import edu.example.hw1.domain.entity.Operation;
import edu.example.hw1.domain.entity.Operation.OperationType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends MongoRepository<Operation, String> {
    List<Operation> findAllByType(OperationType type);
}
