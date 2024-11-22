package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.data.ChannelDto;
import io.usnack.simplechat.dto.request.ChannelCreateRequest;
import io.usnack.simplechat.dto.request.ChannelUpdateRequest;
import io.usnack.simplechat.entity.Channel;
import io.usnack.simplechat.entity.ChannelType;
import io.usnack.simplechat.mapstruct.ChannelMapper;
import io.usnack.simplechat.repository.ChannelRepository;
import io.usnack.simplechat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;
    //
    private final UserRepository userRepository;

    @Transactional
    public ChannelDto createChannel(ChannelCreateRequest request) {
        ChannelType type = request.type();
        String name = request.name();
        String description = request.description();
        UUID ownerId = request.ownerId();
        long now = Instant.now().toEpochMilli();

        if (!userRepository.existsById(ownerId)) {
            throw new NoSuchElementException("User with id " + ownerId + " does not exist");
        }

        Channel channelToCreate = new Channel(type, name, description, ownerId, now);

        Channel createdChannel = channelRepository.save(channelToCreate);
        ChannelDto response = channelMapper.toDto(createdChannel);
        return response;
    }

    public ChannelDto findChannelById(UUID channelId) {
        return channelRepository.findById(channelId)
                .map(channelMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("Channel with id %s not found", channelId)));
    }

    public List<ChannelDto> findAllChannels() {
        List<ChannelDto> channels = channelRepository.findAll()
                .stream().map(channelMapper::toDto)
                .toList();

        return channels;
    }

    @Transactional
    public ChannelDto modifyChannel(ChannelUpdateRequest request) {
        UUID channelId = request.channelId();
        String name = request.name();
        String description = request.description();
        UUID ownerId = request.ownerId();
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Channel with id %s not found", channelId)));
        channel.updateChannel(name, description, ownerId);

        ChannelDto response = channelMapper.toDto(channel);
        return response;
    }

    @Transactional
    public void deleteChannel(UUID channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException(String.format("Channel with id %s not found", channelId));
        }
        channelRepository.deleteById(channelId);
    }
}
