package com.example.similarfingerprint.repository;

import com.example.similarfingerprint.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
