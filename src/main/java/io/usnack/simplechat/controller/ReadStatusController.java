package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.data.ReadStatusDto;
import io.usnack.simplechat.dto.request.ReadStatusCreateRequest;
import io.usnack.simplechat.dto.request.ReadStatusUpdateRequest;
import io.usnack.simplechat.service.ReadStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("readStatus")
@RestController
public class ReadStatusController {
    private final ReadStatusService readStatusService;

    @PostMapping
    public ResponseEntity<ReadStatusDto> createReadStatus(@Valid @RequestBody ReadStatusCreateRequest request) {
        ReadStatusDto response = readStatusService.createReadStatus(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("{readStatusId}")
    public ResponseEntity<ReadStatusDto> findReadStatus(@PathVariable("readStatusId") UUID readStatusId) {
        ReadStatusDto response = readStatusService.findReadStatus(readStatusId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReadStatusDto>> findAllReadStatusByUserId(@RequestParam("userId") UUID userId) {
        List<ReadStatusDto> response = readStatusService.findAllReadStatusByUserId(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("{readStatusId}")
    public ResponseEntity<ReadStatusDto> updateReadStatus(@PathVariable("readStatusId") UUID readStatusId, @Valid @RequestBody ReadStatusUpdateRequest request) {
        ReadStatusDto response = readStatusService.updateReadStatus(readStatusId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{readStatusId}")
    public ResponseEntity<Void> deleteReadStatus(@PathVariable("readStatusId") UUID readStatusId) {
        readStatusService.deleteReadStatus(readStatusId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
