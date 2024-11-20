package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.request.UserCreateRequest;
import io.usnack.simplechat.dto.request.UserUpdateRequest;
import io.usnack.simplechat.dto.response.UserDetailResponse;
import io.usnack.simplechat.dto.response.UserListResponse;
import io.usnack.simplechat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDetailResponse> create(@RequestBody UserCreateRequest request) {
        UserDetailResponse response = userService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDetailResponse> findById(@PathVariable("userId") UUID userId) {
        UserDetailResponse response = userService.findUserById(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<UserListResponse> findAll() {
        UserListResponse response = userService.findAllUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping
    public ResponseEntity<UserDetailResponse> update(@RequestBody UserUpdateRequest request) {
        UserDetailResponse response = userService.modifyUser(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity delete(@PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
