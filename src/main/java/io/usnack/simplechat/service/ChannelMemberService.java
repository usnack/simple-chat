package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.request.ChannelMemberCreateRequest;
import io.usnack.simplechat.dto.request.ChannelMemberUpdateRequest;
import io.usnack.simplechat.dto.response.ChannelMemberDetailResponse;
import io.usnack.simplechat.dto.response.ChannelMemberListResponse;
import io.usnack.simplechat.entity.ChannelMember;
import io.usnack.simplechat.mapstruct.ChannelMemberMapper;
import io.usnack.simplechat.repository.ChannelMemberRepository;
import io.usnack.simplechat.repository.ChannelRepository;
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
public class ChannelMemberService {
    private final ChannelMemberRepository channelMemberRepository;
    private final ChannelMemberMapper channelMemberMapper;
    //
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;


    @Transactional
    public ChannelMemberDetailResponse createChannelMember(ChannelMemberCreateRequest request) {
        UUID channelId = request.channelId();
        UUID userId = request.userId();

        long now = Instant.now().toEpochMilli();

        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel with id " + channelId + " does not exist");
        }
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with id " + userId + " does not exist");
        }

        ChannelMember channelMemberToCreate = new ChannelMember(userId, channelId);

        ChannelMember createdChannelMember = channelMemberRepository.save(channelMemberToCreate);
        ChannelMemberDetailResponse response = channelMemberMapper.toDetailResponse(createdChannelMember);
        return response;
    }

    public ChannelMemberDetailResponse findChannelMemberById(UUID channelMemberId) {
        return channelMemberRepository.findById(channelMemberId)
                .map(channelMemberMapper::toDetailResponse)
                .orElseThrow(() -> new NoSuchElementException(String.format("ChannelMember with id %s not found", channelMemberId)));
    }

    public ChannelMemberListResponse findAllChannelMembers() {
        List<ChannelMemberDetailResponse> channelMembers = channelMemberRepository.findAll()
                .stream().map(channelMemberMapper::toDetailResponse)
                .toList();

        return new ChannelMemberListResponse(channelMembers);
    }

    @Transactional
    public ChannelMemberDetailResponse modifyChannelMember(ChannelMemberUpdateRequest request) {
        UUID channelMemberId = request.channelMemberId();
        Long readAt = request.readAt();
        ChannelMember channelMember = channelMemberRepository.findById(channelMemberId)
                .orElseThrow(() -> new NoSuchElementException(String.format("ChannelMember with id %s not found", channelMemberId)));
        channelMember.updateChannelMember(readAt);

        ChannelMemberDetailResponse response = channelMemberMapper.toDetailResponse(channelMember);
        return response;
    }

    @Transactional
    public void deleteChannelMember(UUID channelMemberId) {
        if (!channelMemberRepository.existsById(channelMemberId)) {
            throw new NoSuchElementException(String.format("ChannelMember with id %s not found", channelMemberId));
        }
        channelMemberRepository.deleteById(channelMemberId);
    }
}
