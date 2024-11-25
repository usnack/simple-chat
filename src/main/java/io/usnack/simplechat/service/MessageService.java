package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.data.MessageDto;
import io.usnack.simplechat.dto.data.PageableData;
import io.usnack.simplechat.dto.request.MessageCreateRequest;
import io.usnack.simplechat.dto.request.MessageUpdateRequest;
import io.usnack.simplechat.entity.Attachment;
import io.usnack.simplechat.entity.Message;
import io.usnack.simplechat.entity.User;
import io.usnack.simplechat.mapstruct.MessageMapper;
import io.usnack.simplechat.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    //
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private final BinaryRepository binaryRepository;

    @Transactional
    public MessageDto createMessage(MessageCreateRequest request) {
        String content = request.content();
        UUID channelId = request.channelId();
        UUID authorId = request.authorId();
        List<byte[]> optionalAttachments = request.attachments();

        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel with id " + channelId + " does not exist");
        }
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + authorId + " does not exist"));

        Message messageToCreate = new Message(content, channelId, author);
        Message createdMessage = messageRepository.save(messageToCreate);

        Optional.ofNullable(optionalAttachments)
                .ifPresent(attachments -> attachments.forEach(binary -> {
                    String url = binaryRepository.save(binary);
                    Attachment attachmentToCreate = new Attachment(url, createdMessage);
                    attachmentRepository.save(attachmentToCreate);
                }));

        MessageDto response = messageMapper.toDto(createdMessage);
        return response;
    }

    public MessageDto findMessage(UUID messageId) {
        return messageRepository.findById(messageId)
                .map(messageMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("Message with id %s not found", messageId)));
    }

    public PageableData<MessageDto> findAllMessagesByChannelId(UUID channelId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Message> messagePage = messageRepository.findByChannelId(channelId, pageRequest);

        List<MessageDto> messageDtos = messagePage
                .stream().map(message -> messageMapper.toDto(message))
                .sorted(Comparator.comparingLong(MessageDto::createdAt))
                .toList();

        boolean hasMore = messagePage.hasNext();
        PageableData<MessageDto> response = new PageableData<>(messageDtos, hasMore, hasMore ? page + 1 : page, size);
        return response;
    }

    @Transactional
    public MessageDto updateMessage(MessageUpdateRequest request) {
        UUID messageId = request.messageId();
        String content = request.content();

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Message with id %s not found", messageId)));
        message.updateMessage(content);

        MessageDto response = messageMapper.toDto(message);
        return response;
    }

    @Transactional
    public void deleteMessage(UUID messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new NoSuchElementException(String.format("Message with id %s not found", messageId));
        }
        messageRepository.deleteById(messageId);
    }
}
