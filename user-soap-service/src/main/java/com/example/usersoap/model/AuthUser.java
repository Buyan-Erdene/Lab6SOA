package com.example.usersoap.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "auth_users")   // @Entity → @Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {

    @Id
    private String id;                 // Long → String

    @Indexed(unique = true)
    private String username;

    private String password;

    @Indexed(unique = true)
    private String email;

    private String token;
}