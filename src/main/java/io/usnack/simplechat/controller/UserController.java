package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.data.UserDto;
import io.usnack.simplechat.dto.request.UserCreateRequest;
import io.usnack.simplechat.dto.request.UserUpdateRequest;
import io.usnack.simplechat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateRequest request) {
        UserDto response = userService.createUser(request);
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

    @PatchMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserUpdateRequest request) {
        UserDto response = userService.updateUser(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
