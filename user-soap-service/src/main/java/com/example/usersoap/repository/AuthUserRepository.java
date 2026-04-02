package com.example.usersoap.repository;

import com.example.usersoap.model.AuthUser;
import org.springframework.data.mongodb.repository.MongoRepository;  // JpaRepository → MongoRepository
import java.util.Optional;

public interface AuthUserRepository extends MongoRepository<AuthUser, String> {  // Long → String
    Optional<AuthUser> findByUsername(String username);
    Optional<AuthUser> findByToken(String token);
}