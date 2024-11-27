package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.data.UserStatusDto;
import io.usnack.simplechat.dto.request.UserStatusUpdateRequest;
import io.usnack.simplechat.service.UserStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("userStatus")
@RestController
public class UserStatusController {
    private final UserStatusService userStatusService;

    @GetMapping("{userStatusId}")
    public ResponseEntity<UserStatusDto> findUserStatus(@PathVariable("userStatusId") UUID userStatusId) {
        UserStatusDto response = userStatusService.findUserStatus(userStatusId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserStatusDto>> findAllUserStatus() {
        List<UserStatusDto> response = userStatusService.findAllUserStatus();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("{userStatusId}")
    public ResponseEntity<UserStatusDto> updateUserStatus(@PathVariable("userStatusId") UUID userStatusId, @Valid @RequestBody UserStatusUpdateRequest request) {
        UserStatusDto response = userStatusService.updateUserStatus(userStatusId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{userStatusId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userStatusId") UUID userStatusId) {
        userStatusService.deleteUserStatus(userStatusId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
