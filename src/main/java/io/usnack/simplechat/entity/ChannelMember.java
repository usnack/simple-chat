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
@Table(name = "channelMembers")
public class ChannelMember {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private UUID userId;
    private UUID channelId;
    private Long readAt;

    public ChannelMember(UUID userId, UUID channelId) {
        this.userId = userId;
        this.channelId = channelId;
        this.readAt = -1L;
    }

    public void updateChannelMember(Long readAt) {
        this.readAt = readAt;
    }

}
