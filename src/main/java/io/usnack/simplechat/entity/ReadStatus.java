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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "readStatus")
public class ReadStatus {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long createdAt;
    private Long updatedAt;

    private UUID userId;
    private UUID channelId;
    private Long lastReadAt;


    public ReadStatus(UUID userId, UUID channelId) {
        this.createdAt = Instant.now().toEpochMilli();
        this.userId = userId;
        this.channelId = channelId;
    }

    public void updateReadStatus(Long lastReadAt) {
        this.updatedAt = Instant.now().toEpochMilli();
        this.lastReadAt = lastReadAt;
    }
}
