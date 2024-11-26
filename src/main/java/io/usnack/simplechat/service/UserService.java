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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final UserStatusRepository userStatusRepository;
    private final BinaryContentService binaryContentService;

    @Transactional
    public UserDto createUser(UserCreateRequest request, Optional<BinaryContentCreateRequest> profileImageRequest) {
        String username = request.username();
        String email = request.email();
        String password = request.password();

        BinaryContent profileBinaryContent = profileImageRequest
                .map(binaryContentService::createBinaryContent)
                .orElse(null);

        User userToCreate = new User(username, email, password, profileBinaryContent);
        User createdUser = userRepository.save(userToCreate);

        UserStatus userStatusToCreate = new UserStatus(createdUser, true, Instant.now().getEpochSecond());
        userStatusRepository.save(userStatusToCreate);

        UserDto response = userMapper.toDto(createdUser);
        return response;
    }

    public UserDto findUser(UUID userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("User with id %s not found", userId)));
    }

    public List<UserDto> findAllUsers() {
        List<UserDto> categories = userRepository.findAll()
                .stream().map(userMapper::toDto)
                .toList();

        return categories;
    }

    @Transactional
    public UserDto updateUser(UserUpdateRequest request) {
        UUID userId = request.userId();
        String username = request.username();
        String email = request.email();
        String password = request.password();
        BinaryContentCreateRequest binaryContentCreateRequest = request.profileImage();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("User with id %s not found", userId)));

        BinaryContent profileBinaryContent = binaryContentService.createBinaryContent(binaryContentCreateRequest);

        user.updateUser(username, email, password, profileBinaryContent);

        UserDto response = userMapper.toDto(user);
        return response;
    }

    @Transactional
    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException(String.format("User with id %s not found", userId));
        }
        userRepository.deleteById(userId);
    }
}
