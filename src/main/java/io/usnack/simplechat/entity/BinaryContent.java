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
@Table(name = "binaryContents")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BinaryContent {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long createdAt;

    private String fileName;
    private Long size;
    private String contentType;

    public BinaryContent(String fileName, Long size, String contentType) {
        this.createdAt = Instant.now().toEpochMilli();

        this.fileName = fileName;
        this.size = size;
        this.contentType = contentType;
    }
}
