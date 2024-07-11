package com.dailycodebuffer.cricket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "players")
@Data
public class PlayerMongoDBEntity {

    @Id
    @Field("_id")
    private Integer playerId;
    private String name;
    private String role;
}
