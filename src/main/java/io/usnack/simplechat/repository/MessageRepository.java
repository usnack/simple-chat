package io.usnack.simplechat.repository;

import io.usnack.simplechat.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    Page<Message> findByChannelId(UUID channelId, Pageable pageable);
}
