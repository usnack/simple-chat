package io.usnack.simplechat.repository;

import io.usnack.simplechat.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChannelRepository extends JpaRepository<Channel, UUID> {
}
