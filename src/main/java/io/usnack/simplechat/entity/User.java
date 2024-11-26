package io.usnack.simplechat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
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
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private BinaryContent profile;
    @OneToOne(mappedBy = "user")
    private UserStatus status;

    public User(String username, String email, String password, BinaryContent profile) {
        this.createdAt = Instant.now().toEpochMilli();
        this.username = username;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    public void updateUser(String username, String email, String password, BinaryContent profile) {
        long now = Instant.now().toEpochMilli();
        if (username != null && !username.equals(this.username)) {
            this.username = username;
            this.updatedAt = now;
        }
        if (email != null && !email.equals(this.email)) {
            this.email = email;
            this.updatedAt = now;
        }
        if (password != null && !password.equals(this.password)) {
            this.password = password;
            this.updatedAt = now;
        }
        if (profile != null && !profile.equals(this.profile)) {
            this.profile = profile;
            this.updatedAt = now;
        }
    }
}