package io.usnack.simplechat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Getter
@ToString(exclude = "message")
@Entity
@Table(name = "messageAssets")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachment {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long createdAt;

    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    public Attachment(String url, Message message) {
        this.createdAt = Instant.now().toEpochMilli();
        this.url = url;
        this.message = message;
    }
}
