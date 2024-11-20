package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.data.ReadStatusDto;
import io.usnack.simplechat.dto.request.ReadStatusCreateRequest;
import io.usnack.simplechat.dto.request.ReadStatusUpdateRequest;
import io.usnack.simplechat.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("ReadStatuss")
@RestController
public class ReadStatusController {
    private final ReadStatusService readStatusService;

    @PostMapping
    public ResponseEntity<ReadStatusDto> create(@RequestBody ReadStatusCreateRequest request) {
        ReadStatusDto response = readStatusService.createReadStatus(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("{readStatusId}")
    public ResponseEntity<ReadStatusDto> findById(@PathVariable("readStatusId") UUID readStatusId) {
        ReadStatusDto response = readStatusService.findReadStatusById(readStatusId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReadStatusDto>> findByUserId(@RequestParam("userId") UUID userId) {
        List<ReadStatusDto> response = readStatusService.findByUserId(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping
    public ResponseEntity<ReadStatusDto> update(@RequestBody ReadStatusUpdateRequest request) {
        ReadStatusDto response = readStatusService.modifyReadStatus(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{readStatusId}")
    public ResponseEntity delete(@PathVariable("readStatusId") UUID readStatusId) {
        readStatusService.deleteReadStatus(readStatusId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
