package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.request.MessageCreateRequest;
import io.usnack.simplechat.dto.request.MessageUpdateRequest;
import io.usnack.simplechat.dto.response.MessageDetailResponse;
import io.usnack.simplechat.dto.response.MessageListResponse;
import io.usnack.simplechat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("messages")
@RestController
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDetailResponse> create(@RequestBody MessageCreateRequest request) {
        MessageDetailResponse response = messageService.createMessage(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("{messageId}")
    public ResponseEntity<MessageDetailResponse> findById(@PathVariable("messageId") UUID messageId) {
        MessageDetailResponse response = messageService.findMessageById(messageId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<MessageListResponse> findAll() {
        MessageListResponse response = messageService.findAllMessages();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping
    public ResponseEntity<MessageDetailResponse> update(@RequestBody MessageUpdateRequest request) {
        MessageDetailResponse response = messageService.modifyMessage(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{messageId}")
    public ResponseEntity delete(@PathVariable("messageId") UUID messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
