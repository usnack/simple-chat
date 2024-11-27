package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.data.ReadStatusDto;
import io.usnack.simplechat.dto.request.ReadStatusCreateRequest;
import io.usnack.simplechat.dto.request.ReadStatusUpdateRequest;
import io.usnack.simplechat.entity.Channel;
import io.usnack.simplechat.entity.ReadStatus;
import io.usnack.simplechat.entity.User;
import io.usnack.simplechat.mapstruct.ReadStatusMapper;
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
public class ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final ReadStatusMapper readStatusMapper;
    //
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;


    @Transactional
    public ReadStatusDto createReadStatus(ReadStatusCreateRequest request) {
        UUID channelId = request.channelId();
        UUID userId = request.userId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User " + userId + " does not exist"));
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("No channel with id " + channelId));

        ReadStatus readStatusToCreate = new ReadStatus(user, channel);

        ReadStatus createdReadStatus = readStatusRepository.save(readStatusToCreate);
        return readStatusMapper.toDto(createdReadStatus);
    }

    public ReadStatusDto findReadStatus(UUID readStatusId) {
        return readStatusRepository.findById(readStatusId)
                .map(readStatusMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("ReadStatus with id %s not found", readStatusId)));
    }

    public List<ReadStatusDto> findAllReadStatusByUserId(UUID userId) {
        return readStatusRepository.findByUserId(userId)
                .stream().map(readStatusMapper::toDto)
                .toList();
    }

    @Transactional
    public ReadStatusDto updateReadStatus(UUID readStatusId, ReadStatusUpdateRequest request) {
        Long readAt = request.readAt();

        ReadStatus readStatus = readStatusRepository.findById(readStatusId)
                .orElseThrow(() -> new NoSuchElementException(String.format("readStatus with id %s not found", readStatusId)));
        readStatus.updateReadStatus(readAt);

        return readStatusMapper.toDto(readStatus);
    }

    @Transactional
    public void deleteReadStatus(UUID readStatusId) {
        if (!readStatusRepository.existsById(readStatusId)) {
            throw new NoSuchElementException(String.format("ReadStatus with id %s not found", readStatusId));
        }
        readStatusRepository.deleteById(readStatusId);
    }
}
