package io.usnack.simplechat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "readStatus")
public class ReadStatus {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private UUID userId;
    private UUID channelId;
    private Long readAt;

    public ReadStatus(UUID userId, UUID channelId, Long readAt) {
        this.userId = userId;
        this.channelId = channelId;
        this.readAt = readAt;
    }

    public void updateReadAt(Long readAt) {
        this.readAt = readAt;
    }
}
