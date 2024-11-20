package io.usnack.simplechat.repository;

import io.usnack.simplechat.entity.ChannelMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, UUID> {
}
