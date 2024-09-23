package com.example.similarfingerprint.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class UserEntity extends BaseEntity{
    private String userName;
    private String userData;
}
