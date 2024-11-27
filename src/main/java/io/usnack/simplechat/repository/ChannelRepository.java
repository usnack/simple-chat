package io.usnack.simplechat.repository;

import io.usnack.simplechat.entity.Channel;
import io.usnack.simplechat.entity.ChannelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChannelRepository extends JpaRepository<Channel, UUID> {
    List<Channel> findAllByTypeOrIdIn(ChannelType type, List<UUID> ids);
}
