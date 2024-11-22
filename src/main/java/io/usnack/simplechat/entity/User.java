package io.usnack.simplechat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Getter
@ToString
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long createdAt;
    private Long updatedAt;

    private String username;
    private String email;
    private String password;
    private String profileUrl;

    public User(String username, String email, String password, String profileUrl) {
        this.createdAt = Instant.now().toEpochMilli();
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileUrl = profileUrl;
    }

    public void updateUser(String username, String email, String password, String profileUrl) {
        long now = Instant.now().toEpochMilli();
        Optional.ofNullable(username).ifPresent(value -> {
            this.username = value;
            this.updatedAt = now;
        });
        Optional.ofNullable(email).ifPresent(value -> {
            this.email = value;
            this.updatedAt = now;
        });
        Optional.ofNullable(password).ifPresent(value -> {
            this.password = value;
            this.updatedAt = now;
        });
        Optional.ofNullable(profileUrl).ifPresent(value -> {
            this.profileUrl = value;
            this.updatedAt = now;
        });
    }
}