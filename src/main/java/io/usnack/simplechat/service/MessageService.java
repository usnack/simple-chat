package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.data.MessageDto;
import io.usnack.simplechat.dto.request.MessageCreateRequest;
import io.usnack.simplechat.dto.request.MessageUpdateRequest;
import io.usnack.simplechat.entity.Message;
import io.usnack.simplechat.mapstruct.MessageMapper;
import io.usnack.simplechat.repository.ChannelRepository;
import io.usnack.simplechat.repository.MessageRepository;
import io.usnack.simplechat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    //
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    @Transactional
    public MessageDto createMessage(MessageCreateRequest request) {
        UUID channelId = request.channelId();
        UUID authorId = request.authorId();
        String content = request.content();
        long now = Instant.now().toEpochMilli();

        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel with id " + channelId + " does not exist");
        }
        if (!userRepository.existsById(authorId)) {
            throw new NoSuchElementException("User with id " + authorId + " does not exist");
        }

        Message messageToCreate = new Message(content, authorId, channelId, now);

        Message createdMessage = messageRepository.save(messageToCreate);
        MessageDto response = messageMapper.toDto(createdMessage);
        return response;
    }

    public MessageDto findMessageById(UUID messageId) {
        return messageRepository.findById(messageId)
                .map(messageMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("Message with id %s not found", messageId)));
    }

    public List<MessageDto> findAllMessages() {
        List<MessageDto> messages = messageRepository.findAll()
                .stream().map(messageMapper::toDto)
                .toList();

        return messages;
    }

    @Transactional
    public MessageDto modifyMessage(MessageUpdateRequest request) {
        UUID messageId = request.messageId();
        String content = request.content();
        long now = Instant.now().toEpochMilli();

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Message with id %s not found", messageId)));
        message.updateMessage(content, now);

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
