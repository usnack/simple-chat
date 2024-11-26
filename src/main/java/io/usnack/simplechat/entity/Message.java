package io.usnack.simplechat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@ToString(exclude = {"attachments", "author"})
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
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "message_attachments",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "binary_content_id")
    )
    private List<BinaryContent> attachments = new ArrayList<>();

    public Message(String content, UUID channelId, User author, List<BinaryContent> attachments) {
        this.createdAt = Instant.now().toEpochMilli();
        this.content = content;
        this.channelId = channelId;
        this.author = author;
        this.attachments = attachments;
    }

    public void updateMessage(
            String content
    ) {
        long now = Instant.now().toEpochMilli();
        if (content != null && !content.equals(this.content)) {
            this.content = content;
            this.updatedAt = now;
        }
    }
}