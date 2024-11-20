package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.request.ChannelMemberCreateRequest;
import io.usnack.simplechat.dto.request.ChannelMemberUpdateRequest;
import io.usnack.simplechat.dto.response.ChannelMemberDetailResponse;
import io.usnack.simplechat.dto.response.ChannelMemberListResponse;
import io.usnack.simplechat.service.ChannelMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("channelMembers")
@RestController
public class ChannelMemberController {
    private final ChannelMemberService channelMemberService;

    @PostMapping
    public ResponseEntity<ChannelMemberDetailResponse> create(@RequestBody ChannelMemberCreateRequest request) {
        ChannelMemberDetailResponse response = channelMemberService.createChannelMember(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("{channelMemberId}")
    public ResponseEntity<ChannelMemberDetailResponse> findById(@PathVariable("channelMemberId") UUID channelMemberId) {
        ChannelMemberDetailResponse response = channelMemberService.findChannelMemberById(channelMemberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ChannelMemberListResponse> findAll() {
        ChannelMemberListResponse response = channelMemberService.findAllChannelMembers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping
    public ResponseEntity<ChannelMemberDetailResponse> update(@RequestBody ChannelMemberUpdateRequest request) {
        ChannelMemberDetailResponse response = channelMemberService.modifyChannelMember(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{channelMemberId}")
    public ResponseEntity delete(@PathVariable("channelMemberId") UUID channelMemberId) {
        channelMemberService.deleteChannelMember(channelMemberId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
