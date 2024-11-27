package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.data.ChannelDto;
import io.usnack.simplechat.dto.request.PrivateChannelCreateRequest;
import io.usnack.simplechat.dto.request.PublicChannelCreateRequest;
import io.usnack.simplechat.dto.request.PublicChannelUpdateRequest;
import io.usnack.simplechat.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("channels")
@RestController
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping("public")
    public ResponseEntity<ChannelDto> createPublicChannel(@RequestBody PublicChannelCreateRequest request) {
        ChannelDto response = channelService.createPublicChannel(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("private")
    public ResponseEntity<ChannelDto> createPrivateChannel(@RequestBody PrivateChannelCreateRequest request) {
        ChannelDto response = channelService.createPrivateChannel(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("{channelId}")
    public ResponseEntity<ChannelDto> findChannel(@PathVariable("channelId") UUID channelId) {
        ChannelDto response = channelService.findChannel(channelId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ChannelDto>> findAllChannels(@RequestParam("userId") UUID userId) {
        List<ChannelDto> response = channelService.findAllChannels(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("{channelId}")
    public ResponseEntity<ChannelDto> updateChannel(@PathVariable("channelId") UUID channelId, @RequestBody PublicChannelUpdateRequest request) {
        ChannelDto response = channelService.updateGroupChannel(channelId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{channelId}")
    public ResponseEntity<Void> deleteChannel(@PathVariable("channelId") UUID channelId) {
        channelService.deleteChannel(channelId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
