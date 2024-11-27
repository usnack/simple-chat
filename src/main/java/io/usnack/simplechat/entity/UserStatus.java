package io.usnack.simplechat.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Getter
@ToString(exclude = "user")
@Entity
@Table(name = "userStatus")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStatus {
    public static final long activeThreshold = 1000 * 60 * 10; // 10 minutes
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long createdAt;
    private Long updatedAt;

    private Long lastActiveAt;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserStatus(User user, Long lastActiveAt) {
        this.createdAt = Instant.now().getEpochSecond();
        this.user = user;
        this.lastActiveAt = lastActiveAt;
    }

    public void updateUserStatus(Long lastActiveAt) {
        long now = Instant.now().getEpochSecond();
        if (lastActiveAt != null && !lastActiveAt.equals(this.lastActiveAt)) {
            this.lastActiveAt = lastActiveAt;
            this.updatedAt = now;
        }
    }

    public boolean isActive() {
        long now = Instant.now().getEpochSecond();
        return now - this.lastActiveAt < activeThreshold;
    }
}
