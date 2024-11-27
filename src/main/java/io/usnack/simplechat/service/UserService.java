package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.data.UserDto;
import io.usnack.simplechat.dto.request.BinaryContentCreateRequest;
import io.usnack.simplechat.dto.request.UserCreateRequest;
import io.usnack.simplechat.dto.request.UserUpdateRequest;
import io.usnack.simplechat.entity.BinaryContent;
import io.usnack.simplechat.entity.User;
import io.usnack.simplechat.entity.UserStatus;
import io.usnack.simplechat.mapstruct.UserMapper;
import io.usnack.simplechat.repository.UserRepository;
import io.usnack.simplechat.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final UserStatusRepository userStatusRepository;
    private final BinaryContentService binaryContentService;

    @Transactional
    public UserDto createUser(UserCreateRequest request, Optional<BinaryContentCreateRequest> optionalProfileRequest) {
        // create profile
        BinaryContent profileBinaryContent = optionalProfileRequest
                .map(binaryContentService::createBinaryContent)
                .orElse(null);

        // create user
        String username = request.username();
        String email = request.email();
        String password = request.password();
        User userToCreate = new User(username, email, password, profileBinaryContent);
        User createdUser = userRepository.save(userToCreate);

        // create userStatus
        UserStatus userStatusToCreate = new UserStatus(createdUser, Instant.now().getEpochSecond());
        userStatusRepository.save(userStatusToCreate);

        return userMapper.toDto(createdUser);
    }

    public UserDto findUser(UUID userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("User with id %s not found", userId)));
    }

    public List<UserDto> findAllUsers() {
        List<UserDto> users = userRepository.findAll()
                .stream().map(userMapper::toDto)
                .toList();

        return users;
    }

    @Transactional
    public UserDto updateUser(UUID userId, UserUpdateRequest request, Optional<BinaryContentCreateRequest> optionalProfileRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("User with id %s not found", userId)));

        // optional update profile
        BinaryContent profile = optionalProfileRequest
                .map(binaryContentService::createBinaryContent)
                .orElse(user.getProfile());

        // user
        String username = request.username();
        String email = request.email();
        String password = request.password();
        user.updateUser(username, email, password, profile);

        return userMapper.toDto(user);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException(String.format("User with id %s not found", userId));
        }
        userRepository.deleteById(userId);
    }
}
