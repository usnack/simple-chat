package io.usnack.simplechat.service;

import io.usnack.simplechat.dto.data.BinaryContentInputStreamDto;
import io.usnack.simplechat.dto.request.BinaryContentCreateRequest;
import io.usnack.simplechat.entity.BinaryContent;
import io.usnack.simplechat.mapstruct.BinaryContentMapper;
import io.usnack.simplechat.repository.BinaryContentRepository;
import io.usnack.simplechat.util.binary.BinaryStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;
    private final BinaryStorage binaryStorage;
    private final BinaryContentMapper binaryContentMapper;

    @Transactional
    protected BinaryContent createBinaryContent(BinaryContentCreateRequest request) {
        String fileName = request.fileName();
        String contentType = request.contentType();
        byte[] bytes = request.bytes();

        BinaryContent binaryContentToCreate = new BinaryContent(fileName, (long) bytes.length, contentType);
        BinaryContent createdBinaryContent = binaryContentRepository.save(binaryContentToCreate);

        UUID binaryContentIdAsKey = createdBinaryContent.getId();
        binaryStorage.saveBinary(binaryContentIdAsKey, bytes);

        return createdBinaryContent;
    }

    public BinaryContentInputStreamDto loadBinaryContent(UUID binaryContentId) {
        return binaryContentRepository.findById(binaryContentId)
                .map(binaryContentMapper::toInputStreamDto)
                .orElseThrow(() -> new NoSuchElementException("Binary content with id " + binaryContentId + " not found"));
    }
}
