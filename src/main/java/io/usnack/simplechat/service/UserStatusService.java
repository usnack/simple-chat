package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.data.UserStatusDto;
import io.usnack.simplechat.dto.request.UserStatusUpdateRequest;
import io.usnack.simplechat.entity.UserStatus;
import io.usnack.simplechat.mapstruct.UserStatusMapper;
import io.usnack.simplechat.repository.UserRepository;
import io.usnack.simplechat.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserStatusMapper userStatusMapper;

    private final UserRepository userRepository;

    public UserStatusDto findUserStatus(UUID userStatusId) {
        return userStatusRepository.findById(userStatusId)
                .map(userStatusMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("UserStatus with id " + userStatusId + " does not exist"));
    }

    public List<UserStatusDto> findAllUserStatus() {
        List<UserStatusDto> userStatusList = userStatusRepository.findAll()
                .stream().map(userStatusMapper::toDto)
                .toList();

        return userStatusList;
    }

    @Transactional
    public UserStatusDto updateUserStatus(UserStatusUpdateRequest request) {
        UUID userStatusId = request.userStatusId();
        Boolean online = request.online();
        Long lastActiveAt = request.lastActiveAt();

        UserStatus userStatus = userStatusRepository.findById(userStatusId)
                .orElseThrow(() -> new NoSuchElementException("UserStatus with id " + userStatusId + " does not exist"));
        userStatus.updateUserStatus(online, lastActiveAt);

        UserStatusDto userStatusDto = userStatusMapper.toDto(userStatus);
        return userStatusDto;
    }

    @Transactional
    public void deleteUserStatus(UUID userStatusId) {
        if (!userStatusRepository.existsById(userStatusId)) {
            throw new NoSuchElementException("UserStatus with id " + userStatusId + " does not exist");
        }
        userStatusRepository.deleteById(userStatusId);
    }
}
