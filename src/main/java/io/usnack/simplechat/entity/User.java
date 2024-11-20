package io.usnack.simplechat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String username;
    private String email;
    private String password;
    private String avatarUrl;
    private Long createdAt;

    public User(String username, String email, String password, String avatarUrl, Long createdAt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
    }

    public void updateUser(String username, String password, String email, String avatarUrl) {
        Optional.ofNullable(username).ifPresent(value -> this.username = value);
        Optional.ofNullable(password).ifPresent(value -> this.password = value);
        Optional.ofNullable(email).ifPresent(value -> this.email = value);
        Optional.ofNullable(avatarUrl).ifPresent(value -> this.avatarUrl = value);
    }
}