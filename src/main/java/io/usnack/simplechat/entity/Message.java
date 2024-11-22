package io.usnack.simplechat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@ToString
@Entity
@Table(name = "messages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long createdAt;
    private Long updatedAt;

    private String content;
    private UUID channelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId", nullable = false)
    private User author;
    @OneToMany(mappedBy = "message")
    private List<MessageAsset> messageAssets = new ArrayList<>();

    public Message(String content, UUID channelId, User author) {
        this.createdAt = Instant.now().toEpochMilli();
        this.content = content;
        this.channelId = channelId;
        this.author = author;
    }

    public void updateMessage(
            String content, List<MessageAsset> messageAssets
    ) {
        long now = Instant.now().toEpochMilli();
        Optional.ofNullable(content).ifPresent(value -> {
            this.content = value;
            this.updatedAt = now;
        });
        Optional.ofNullable(messageAssets).ifPresent(value -> {
            this.messageAssets = value;
            this.updatedAt = now;
        });
    }
}