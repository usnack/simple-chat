package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.data.MessageDto;
import io.usnack.simplechat.dto.data.PageableData;
import io.usnack.simplechat.dto.request.MessageCreateRequest;
import io.usnack.simplechat.dto.request.MessageUpdateRequest;
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
    public ResponseEntity<MessageDto> create(@RequestBody MessageCreateRequest request) {
        MessageDto response = messageService.createMessage(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("{messageId}")
    public ResponseEntity<MessageDto> findById(@PathVariable("messageId") UUID messageId) {
        MessageDto response = messageService.findMessageById(messageId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<PageableData<MessageDto>> findByChannelId(
            @RequestParam("channelId") UUID channelId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "50", required = false) int size
    ) {
        PageableData<MessageDto> response = messageService.findMessagesByChannelId(channelId, page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping
    public ResponseEntity<MessageDto> update(@RequestBody MessageUpdateRequest request) {
        MessageDto response = messageService.modifyMessage(request);
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
