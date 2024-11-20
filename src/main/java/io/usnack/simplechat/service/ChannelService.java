package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.request.ChannelCreateRequest;
import io.usnack.simplechat.dto.request.ChannelUpdateRequest;
import io.usnack.simplechat.dto.response.ChannelDetailResponse;
import io.usnack.simplechat.dto.response.ChannelListResponse;
import io.usnack.simplechat.entity.Channel;
import io.usnack.simplechat.entity.ChannelType;
import io.usnack.simplechat.mapstruct.ChannelMapper;
import io.usnack.simplechat.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    @Transactional
    public ChannelDetailResponse createChannel(ChannelCreateRequest request) {
        ChannelType type = request.type();
        String name = request.name();
        String description = request.description();
        UUID categoryId = request.categoryId();
        UUID ownerId = request.ownerId();
        long now = Instant.now().toEpochMilli();

        if (!categoryRepository.existsById(categoryId)) {
            throw new NoSuchElementException("Category with id " + categoryId + " does not exist");
        }
        if (!userRepository.existsById(ownerId)) {
            throw new NoSuchElementException("User with id " + ownerId + " does not exist");
        }

        Channel channelToCreate = new Channel(type, name, description, categoryId, ownerId, now);

        Channel createdChannel = channelRepository.save(channelToCreate);
        ChannelDetailResponse response = channelMapper.toDetailResponse(createdChannel);
        return response;
    }

    public ChannelDetailResponse findChannelById(UUID channelId) {
        return channelRepository.findById(channelId)
                .map(channelMapper::toDetailResponse)
                .orElseThrow(() -> new NoSuchElementException(String.format("Channel with id %s not found", channelId)));
    }

    public ChannelListResponse findAllChannels() {
        List<ChannelDetailResponse> channels = channelRepository.findAll()
                .stream().map(channelMapper::toDetailResponse)
                .toList();

        return new ChannelListResponse(channels);
    }

    @Transactional
    public ChannelDetailResponse modifyChannel(ChannelUpdateRequest request) {
        UUID channelId = request.categoryId();
        String name = request.name();
        String description = request.description();
        UUID categoryId = request.categoryId();
        UUID ownerId = request.ownerId();
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Channel with id %s not found", channelId)));
        channel.updateChannel(name, description, categoryId, ownerId);

        ChannelDetailResponse response = channelMapper.toDetailResponse(channel);
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
