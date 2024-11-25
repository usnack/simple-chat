package io.usnack.simplechat.dto.data;

import io.usnack.simplechat.entity.ChannelType;

import java.util.UUID;

public record ChannelDto(
        UUID id,
        ChannelType type,
        String name,
        String description
) {}
