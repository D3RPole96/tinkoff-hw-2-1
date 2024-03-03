package edu.example.hw1.domain.entity;

import edu.example.hw1.api.dto.OperationDto;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@AllArgsConstructor
@Accessors(chain = true)
public class Operation {
    @Id
    private String id;
    private String content;
    private LocalDateTime date;
    private OperationType type;

    public enum OperationType {
        WRITE, READ, DELETE;
    }
}
