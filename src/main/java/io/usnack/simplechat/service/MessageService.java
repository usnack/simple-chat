package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.data.MessageDto;
import io.usnack.simplechat.dto.data.PageableData;
import io.usnack.simplechat.dto.request.MessageCreateRequest;
import io.usnack.simplechat.dto.request.MessageUpdateRequest;
import io.usnack.simplechat.entity.Channel;
import io.usnack.simplechat.entity.Message;
import io.usnack.simplechat.entity.User;
import io.usnack.simplechat.mapstruct.MessageMapper;
import io.usnack.simplechat.mapstruct.UserMapper;
import io.usnack.simplechat.repository.ChannelRepository;
import io.usnack.simplechat.repository.MessageRepository;
import io.usnack.simplechat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
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
    private final UserMapper userMapper;

    @Transactional
    public MessageDto createMessage(MessageCreateRequest request) {
        UUID channelId = request.channelId();
        UUID authorId = request.authorId();
        String content = request.content();
        long now = Instant.now().toEpochMilli();

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelId + " does not exist"));
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + authorId + " does not exist"));

        Message messageToCreate = new Message(content, author, channelId, now);
        Message createdMessage = messageRepository.save(messageToCreate);
        channel.updateLatestMessageAt(createdMessage.getCreatedAt());

        MessageDto response = messageMapper.toDto(createdMessage);
        return response;
    }

    public MessageDto findMessageById(UUID messageId) {
        return messageRepository.findById(messageId)
                .map(message -> messageMapper.toDto(message))
                .orElseThrow(() -> new NoSuchElementException(String.format("Message with id %s not found", messageId)));
    }

    public PageableData<MessageDto> findMessagesByChannelId(UUID channelId, int page, int size) {
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
