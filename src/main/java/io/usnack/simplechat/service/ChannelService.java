package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.data.ChannelDto;
import io.usnack.simplechat.dto.request.PrivateChannelCreateRequest;
import io.usnack.simplechat.dto.request.PublicChannelCreateRequest;
import io.usnack.simplechat.dto.request.PublicChannelUpdateRequest;
import io.usnack.simplechat.entity.Channel;
import io.usnack.simplechat.entity.ChannelType;
import io.usnack.simplechat.entity.ReadStatus;
import io.usnack.simplechat.entity.User;
import io.usnack.simplechat.mapstruct.ChannelMapper;
import io.usnack.simplechat.repository.ChannelRepository;
import io.usnack.simplechat.repository.ReadStatusRepository;
import io.usnack.simplechat.repository.UserRepository;
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
    //
    private final UserRepository userRepository;
    private final ReadStatusRepository readStatusRepository;

    @Transactional
    public ChannelDto createPublicChannel(PublicChannelCreateRequest request) {
        String name = request.name();
        String description = request.description();

        Channel channelToCreate = new Channel(ChannelType.PUBLIC, name, description);

        Channel createdChannel = channelRepository.save(channelToCreate);
        return channelMapper.toDto(createdChannel);
    }

    @Transactional
    public ChannelDto createPrivateChannel(PrivateChannelCreateRequest request) {
        List<UUID> userIds = request.userIds();

        List<User> users = userRepository.findAllById(userIds);

        String channelName = users.stream().map(User::getUsername).reduce("", (v1, v2) -> String.join(", ", v1, v2));
        Channel channelToCreate = new Channel(ChannelType.PRIVATE, channelName, null);
        Channel createdChannel = channelRepository.save(channelToCreate);

        List<ReadStatus> readStatusListToCreate = users.stream().map(user -> new ReadStatus(user, createdChannel)).toList();
        List<ReadStatus> createdReadStatusList = readStatusRepository.saveAll(readStatusListToCreate);

        return channelMapper.toDto(createdChannel);
    }

    public ChannelDto findChannel(UUID channelId) {
        return channelRepository.findById(channelId)
                .map(channelMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("Channel with id %s not found", channelId)));
    }

    public List<ChannelDto> findAllChannels(UUID userId) {
        List<UUID> channelIds = readStatusRepository.findByUserId(userId)
                .stream().map(ReadStatus::getChannel).map(Channel::getId).toList();

        return channelRepository.findAllByTypeOrIdIn(ChannelType.PUBLIC, channelIds)
                .stream().map(channelMapper::toDto)
                .toList();
    }

    @Transactional
    public ChannelDto updateGroupChannel(UUID channelId, PublicChannelUpdateRequest request) {
        String name = request.name();
        String description = request.description();

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Channel with id %s not found", channelId)));
        if (!channel.getType().equals(ChannelType.PUBLIC)) {
            throw new IllegalArgumentException("Only public channels can be updated");
        }
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
