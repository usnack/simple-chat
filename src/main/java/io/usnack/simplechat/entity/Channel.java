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
@Table(name = "channels")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private ChannelType type;
    private String name;
    private String description;
    private Long createdAt;
    private Long latestMessageAt;
    private UUID ownerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    public Channel(ChannelType type, String name, String description, Category category, UUID ownerId, Long createdAt) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.category = category;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.latestMessageAt = -1L;
    }

    public void updateChannel(
            String name, String description, Category category, UUID ownerId
    ) {
        Optional.ofNullable(name).ifPresent(value -> this.name = value);
        Optional.ofNullable(description).ifPresent(value -> this.description = value);
        Optional.ofNullable(category).ifPresent(value -> this.category = value);
        Optional.ofNullable(ownerId).ifPresent(value -> this.ownerId = value);
    }

    public void updateLatestMessageAt(Long latestMessageAt) {
        this.latestMessageAt = latestMessageAt;
    }
}