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
    private UUID categoryId;
    private UUID ownerId;
    private Long createdAt;
    private Long latestMessageAt;

    public Channel(ChannelType type, String name, String description, UUID categoryId, UUID ownerId, Long createdAt) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.latestMessageAt = -1L;
    }

    public void updateChannel(
            String name, String description, UUID categoryId, UUID ownerId
    ) {
        Optional.ofNullable(name).ifPresent(value -> this.name = value);
        Optional.ofNullable(description).ifPresent(value -> this.description = value);
        Optional.ofNullable(categoryId).ifPresent(value -> this.categoryId = value);
        Optional.ofNullable(ownerId).ifPresent(value -> this.ownerId = value);
    }
}