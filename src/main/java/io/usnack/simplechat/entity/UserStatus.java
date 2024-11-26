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
        if (online != null && !online.equals(this.online)) {
            this.online = online;
            this.updatedAt = now;
        }
        if (lastActiveAt != null && !lastActiveAt.equals(this.lastActiveAt)) {
            this.lastActiveAt = lastActiveAt;
            this.updatedAt = now;
        }
    }
}
