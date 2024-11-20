package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.data.ChannelMemberDto;
import io.usnack.simplechat.dto.request.ChannelMemberCreateRequest;
import io.usnack.simplechat.dto.request.ChannelMemberUpdateRequest;
import io.usnack.simplechat.service.ChannelMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("channelMembers")
@RestController
public class ChannelMemberController {
    private final ChannelMemberService channelMemberService;

    @PostMapping
    public ResponseEntity<ChannelMemberDto> create(@RequestBody ChannelMemberCreateRequest request) {
        ChannelMemberDto response = channelMemberService.createChannelMember(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("{channelMemberId}")
    public ResponseEntity<ChannelMemberDto> findById(@PathVariable("channelMemberId") UUID channelMemberId) {
        ChannelMemberDto response = channelMemberService.findChannelMemberById(channelMemberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ChannelMemberDto>> findAll() {
        List<ChannelMemberDto> response = channelMemberService.findAllChannelMembers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping
    public ResponseEntity<ChannelMemberDto> update(@RequestBody ChannelMemberUpdateRequest request) {
        ChannelMemberDto response = channelMemberService.modifyChannelMember(request);
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
