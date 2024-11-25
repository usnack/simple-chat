package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.data.ReadStatusDto;
import io.usnack.simplechat.dto.request.ReadStatusCreateRequest;
import io.usnack.simplechat.dto.request.ReadStatusUpdateRequest;
import io.usnack.simplechat.entity.ReadStatus;
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

        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel with id " + channelId + " does not exist");
        }
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with id " + userId + " does not exist");
        }

        ReadStatus readStatusToCreate = new ReadStatus(userId, channelId);

        ReadStatus createdReadStatus = readStatusRepository.save(readStatusToCreate);
        ReadStatusDto response = readStatusMapper.toDto(createdReadStatus);
        return response;
    }

    public ReadStatusDto findReadStatus(UUID readStatusId) {
        return readStatusRepository.findById(readStatusId)
                .map(readStatusMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("ReadStatus with id %s not found", readStatusId)));
    }

    public List<ReadStatusDto> findAllReadStatusByUserId(UUID userId) {
        List<ReadStatusDto> readStatuss = readStatusRepository.findByUserId(userId)
                .stream().map(readStatusMapper::toDto)
                .toList();

        return readStatuss;
    }

    @Transactional
    public ReadStatusDto updateReadStatus(ReadStatusUpdateRequest request) {
        UUID readStatusId = request.readStatusId();
        Long readAt = request.readAt();

        ReadStatus readStatus = readStatusRepository.findById(readStatusId)
                .orElseThrow(() -> new NoSuchElementException(String.format("readStatus with id %s not found", readStatusId)));
        readStatus.updateReadStatus(readAt);

        ReadStatusDto response = readStatusMapper.toDto(readStatus);
        return response;
    }

    @Transactional
    public void deleteReadStatus(UUID readStatusId) {
        if (!readStatusRepository.existsById(readStatusId)) {
            throw new NoSuchElementException(String.format("ReadStatus with id %s not found", readStatusId));
        }
        readStatusRepository.deleteById(readStatusId);
    }
}
