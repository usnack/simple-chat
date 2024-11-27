package io.usnack.simplechat.controller;

import io.usnack.simplechat.dto.data.MessageDto;
import io.usnack.simplechat.dto.data.PageableData;
import io.usnack.simplechat.dto.request.BinaryContentCreateRequest;
import io.usnack.simplechat.dto.request.MessageCreateRequest;
import io.usnack.simplechat.dto.request.MessageUpdateRequest;
import io.usnack.simplechat.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("messages")
@RestController
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDto> createMessage(
            @Valid @RequestPart("message") MessageCreateRequest request,
            @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments
    ) {
        List<BinaryContentCreateRequest> attachmentRequests = Optional.ofNullable(attachments)
                .map(files -> files.stream()
                        .map(multipartFile -> {
                            try {
                                return new BinaryContentCreateRequest(
                                        multipartFile.getOriginalFilename(),
                                        multipartFile.getContentType(),
                                        multipartFile.getBytes()
                                );
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList())
                .orElse(null);

        MessageDto response = messageService.createMessage(request, Optional.ofNullable(attachmentRequests));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("{messageId}")
    public ResponseEntity<MessageDto> findMessage(@PathVariable("messageId") UUID messageId) {
        MessageDto response = messageService.findMessage(messageId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<PageableData<MessageDto>> findAllMessagesByChannelId(
            @RequestParam("channelId") UUID channelId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "50", required = false) int size
    ) {
        PageableData<MessageDto> response = messageService.findAllMessagesByChannelId(channelId, page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("{messageId}")
    public ResponseEntity<MessageDto> updateMessage(@PathVariable("messageId") UUID messageId, @Valid @RequestBody MessageUpdateRequest request) {
        MessageDto response = messageService.updateMessage(messageId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable("messageId") UUID messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
