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
@Table(name = "messages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String content;
    private UUID authorId;
    private UUID channelId;
    private Long createdAt;
    private Long updatedAt;

    public Message(String content, UUID authorId, UUID channelId, Long createdAt) {
        this.content = content;
        this.authorId = authorId;
        this.channelId = channelId;
        this.createdAt = createdAt;
        this.updatedAt = -1L;
    }

    public void updateMessage(String content, Long updatedAt) {
        Optional.ofNullable(content).ifPresent(value -> this.content = value);
        this.updatedAt = updatedAt;
    }
}