package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.data.ChannelDto;
import io.usnack.simplechat.dto.request.ChannelCreateRequest;
import io.usnack.simplechat.dto.request.ChannelUpdateRequest;
import io.usnack.simplechat.entity.Channel;
import io.usnack.simplechat.entity.ChannelType;
import io.usnack.simplechat.mapstruct.ChannelMapper;
import io.usnack.simplechat.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;

    @Transactional
    public ChannelDto createChannel(ChannelCreateRequest request) {
        ChannelType type = request.type();
        String name = request.name();
        String description = request.description();

        Channel channelToCreate = new Channel(type, name, description);

        Channel createdChannel = channelRepository.save(channelToCreate);
        return channelMapper.toDto(createdChannel);
    }

    public ChannelDto findChannel(UUID channelId) {
        return channelRepository.findById(channelId)
                .map(channelMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("Channel with id %s not found", channelId)));
    }

    public List<ChannelDto> findAllChannels() {

        return channelRepository.findAll()
                .stream().map(channelMapper::toDto)
                .toList();
    }

    @Transactional
    public ChannelDto updateChannel(UUID channelId, ChannelUpdateRequest request) {
        String name = request.name();
        String description = request.description();

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Channel with id %s not found", channelId)));
        channel.updateChannel(name, description);

        return channelMapper.toDto(channel);
    }

    @Transactional
    public void deleteChannel(UUID channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException(String.format("Channel with id %s not found", channelId));
        }
        channelRepository.deleteById(channelId);
    }
}
