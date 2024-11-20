package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.request.ChannelCreateRequest;
import io.usnack.simplechat.dto.request.ChannelUpdateRequest;
import io.usnack.simplechat.dto.response.ChannelDetailResponse;
import io.usnack.simplechat.dto.response.ChannelListResponse;
import io.usnack.simplechat.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("channels")
@RestController
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping
    public ResponseEntity<ChannelDetailResponse> create(@RequestBody ChannelCreateRequest request) {
        ChannelDetailResponse response = channelService.createChannel(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("{channelId}")
    public ResponseEntity<ChannelDetailResponse> findById(@PathVariable("channelId") UUID channelId) {
        ChannelDetailResponse response = channelService.findChannelById(channelId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ChannelListResponse> findAll() {
        ChannelListResponse response = channelService.findAllChannels();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping
    public ResponseEntity<ChannelDetailResponse> update(@RequestBody ChannelUpdateRequest request) {
        ChannelDetailResponse response = channelService.modifyChannel(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{channelId}")
    public ResponseEntity delete(@PathVariable("channelId") UUID channelId) {
        channelService.deleteChannel(channelId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
