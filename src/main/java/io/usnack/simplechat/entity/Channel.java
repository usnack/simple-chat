package io.usnack.simplechat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
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
    private Long createdAt;
    private Long updatedAt;

    private ChannelType type;
    private String name;
    private String description;

    public Channel(ChannelType type, String name, String description) {
        this.createdAt = Instant.now().toEpochMilli();
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public void updateChannel(
            String name,
            String description
    ) {
        long now = Instant.now().toEpochMilli();
        Optional.ofNullable(name).ifPresent(value -> {
            this.name = value;
            this.updatedAt = now;
        });
        Optional.ofNullable(description).ifPresent(value -> {
            this.description = value;
            this.updatedAt = now;
        });
    }
}