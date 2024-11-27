package io.usnack.simplechat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Getter
@ToString(exclude = {"user", "channel"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "readStatus")
public class ReadStatus {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long createdAt;
    private Long updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;
    private Long lastReadAt;


    public ReadStatus(User user, Channel channel) {
        this.createdAt = Instant.now().toEpochMilli();
        this.user = user;
        this.channel = channel;
    }

    public void updateReadStatus(Long lastReadAt) {
        this.updatedAt = Instant.now().toEpochMilli();
        this.lastReadAt = lastReadAt;
    }
}
