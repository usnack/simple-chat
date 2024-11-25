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
@Table(name = "userStatus")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStatus {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long createdAt;
    private Long updatedAt;

    private Boolean online;
    private Long lastActiveAt;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserStatus(User user, Boolean online, Long lastActiveAt) {
        this.createdAt = Instant.now().getEpochSecond();
        this.user = user;
        this.online = online;
        this.lastActiveAt = lastActiveAt;
    }

    public void updateUserStatus(Boolean online, Long lastActiveAt) {
        long now = Instant.now().getEpochSecond();
        Optional.ofNullable(online).ifPresent(value -> {
            this.online = value;
            this.updatedAt = now;
        });
        Optional.ofNullable(lastActiveAt).ifPresent(value -> {
            this.lastActiveAt = value;
            this.updatedAt = now;
        });
    }
}
