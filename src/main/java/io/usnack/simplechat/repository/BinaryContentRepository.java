package io.usnack.simplechat.repository;

import io.usnack.simplechat.entity.BinaryContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BinaryContentRepository extends JpaRepository<BinaryContent, UUID> {
}
