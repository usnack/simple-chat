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
        if (name != null && !name.equals(this.name)) {
            this.name = name;
            this.updatedAt = now;
        }
        if (description != null && !description.equals(this.description)) {
            this.description = description;
            this.updatedAt = now;
        }
    }
}