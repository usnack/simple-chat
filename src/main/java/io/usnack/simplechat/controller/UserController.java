package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.data.UserDto;
import io.usnack.simplechat.dto.request.BinaryContentCreateRequest;
import io.usnack.simplechat.dto.request.UserCreateRequest;
import io.usnack.simplechat.dto.request.UserUpdateRequest;
import io.usnack.simplechat.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserDto> createUser(
            @Valid @RequestPart("user") UserCreateRequest request,
            @RequestPart(name = "profile", required = false) MultipartFile profile
    ) {
        BinaryContentCreateRequest profileRequest = Optional.ofNullable(profile)
                .map(multipartFile -> {
                    try {
                        return new BinaryContentCreateRequest(multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(null);

        UserDto response = userService.createUser(request, Optional.ofNullable(profileRequest));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDto> findUser(@PathVariable("userId") UUID userId) {
        UserDto response = userService.findUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> response = userService.findAllUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping(path = "{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") UUID userId,
            @Valid @RequestPart("user") UserUpdateRequest request,
            @RequestPart(name = "profile", required = false) MultipartFile profile
    ) {
        BinaryContentCreateRequest profileRequest = Optional.ofNullable(profile)
                .map(multipartFile -> {
                    try {
                        return new BinaryContentCreateRequest(multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(null);

        UserDto response = userService.updateUser(userId, request, Optional.ofNullable(profileRequest));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
