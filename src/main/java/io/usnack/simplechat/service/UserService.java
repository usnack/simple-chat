package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.request.UserCreateRequest;
import io.usnack.simplechat.dto.request.UserUpdateRequest;
import io.usnack.simplechat.dto.response.UserDetailResponse;
import io.usnack.simplechat.dto.response.UserListResponse;
import io.usnack.simplechat.entity.User;
import io.usnack.simplechat.mapstruct.UserMapper;
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
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDetailResponse createUser(UserCreateRequest request) {
        String username = request.username();
        String email = request.email();
        String password = request.password();
        String avatarUrl = request.avatarUrl();
        long now = Instant.now().toEpochMilli();
        User userToCreate = new User(username, email, password, avatarUrl, now);

        User createdUser = userRepository.save(userToCreate);
        UserDetailResponse response = userMapper.toDetailResponse(createdUser);
        return response;
    }

    public UserDetailResponse findUserById(UUID userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDetailResponse)
                .orElseThrow(() -> new NoSuchElementException(String.format("User with id %s not found", userId)));
    }

    public UserListResponse findAllUsers() {
        List<UserDetailResponse> categories = userRepository.findAll()
                .stream().map(userMapper::toDetailResponse)
                .toList();

        return new UserListResponse(categories);
    }

    @Transactional
    public UserDetailResponse modifyUser(UserUpdateRequest request) {
        UUID userId = request.userId();
        String username = request.username();
        String email = request.email();
        String password = request.password();
        String avatarUrl = request.avatarUrl();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("User with id %s not found", userId)));
        user.updateUser(username, email, password, avatarUrl);

        UserDetailResponse response = userMapper.toDetailResponse(user);
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
