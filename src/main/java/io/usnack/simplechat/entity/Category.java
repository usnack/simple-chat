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
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Long createdAt;

    public Category(String name, Long createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    public void updateCategory(String name) {
        Optional.ofNullable(name).ifPresent(value -> this.name = name);
    }
}